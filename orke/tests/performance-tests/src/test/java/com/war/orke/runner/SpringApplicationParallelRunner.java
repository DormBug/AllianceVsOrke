package com.war.orke.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.jsmart.zerocode.core.di.provider.ObjectMapperProvider;
import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.report.ZeroCodeReportGenerator;
import org.jsmart.zerocode.core.report.ZeroCodeReportGeneratorImpl;
import org.jsmart.zerocode.core.runner.parallel.LoadProcessor;
import org.jsmart.zerocode.jupiter.load.JupiterLoadProcessor;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * This runner was created for optimize performance by running tests not in sequence but in parallel.
 * By this class we can easy create 100 and more requests and watch, how many time our project will be process them
 * and also it in twice quicker than run tests in sequence (but tests mustn't interact with each other).
 *
 * */
public class SpringApplicationParallelRunner implements BeforeAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringApplicationParallelRunner.class);
    private final ObjectMapper mapper = new ObjectMapperProvider().get();
    private final ZeroCodeReportGenerator reportGenerator = new ZeroCodeReportGeneratorImpl(mapper);

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws InterruptedException {
        Class<?> testClass = extensionContext.getRequiredTestClass();
        Method[] testClassMethods = testClass.getDeclaredMethods();
        Map<Method, String> methodProperties = validateAndGetLoadPropertiesFile(testClass, testClassMethods);
        Map<Method, LoadProcessor> methodLoadProcessors = methodProperties.keySet().stream()
                .collect(Collectors.toMap(
                        Function.identity(), method -> new JupiterLoadProcessor(methodProperties.get(method))));
        addJupiterTests(methodLoadProcessors);
        Map<Method, Boolean> methodHasFailed = runTestsByLoadProcessor(methodLoadProcessors);
        createTestReports(methodHasFailed, testClass);
    }

    private Map<Method, String> validateAndGetLoadPropertiesFile(Class<?> testClass, Method[] methods) {
        LoadWith loadClassWith = testClass.getAnnotation(LoadWith.class);
        Stream<Method> streamMethods = Stream.of(methods);

        if (Objects.nonNull(loadClassWith)) {
            String value = loadClassWith.value();
            return streamMethods.collect(Collectors.toMap(x -> x, x -> value));
        }

        return streamMethods.collect(Collectors.toMap(
                Function.identity(), method -> getLoadPropertiesByMethod(testClass, method)));
    }

    private String getLoadPropertiesByMethod(Class<?> testClass, Method method) {
        LoadWith loadMethodWith = method.getAnnotation(LoadWith.class);
        if (Objects.nonNull(loadMethodWith)) {
            return loadMethodWith.value();
        }
        throw new RuntimeException(
                String.format("\n<< Ah! Missing the the @LoadWith(...) on this Class '%s' or Method '%s' >> ",
                        testClass.getName(), method.getName())
        );
    }

    private void addJupiterTests(Map<Method, LoadProcessor> methodLoadProcessors) {
        methodLoadProcessors.keySet().forEach(method -> {
            TestMapping[] testMappingArray = method.getAnnotationsByType(TestMapping.class);
            LoadProcessor jupiterLoadProcessor = methodLoadProcessors.get(method);
            Arrays.stream(testMappingArray).forEach(thisMapping ->
                    ((JupiterLoadProcessor)jupiterLoadProcessor)
                            .addJupiterTest(thisMapping.testClass(), thisMapping.testMethod())
            );
        });
    }

    private Map<Method, Boolean> runTestsByLoadProcessor(Map<Method, LoadProcessor> processors) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(processors.size());

        List<Callable<Map.Entry<Method, Boolean>>> callables =
                processors.entrySet().stream().map((entry) -> (Callable<Map.Entry<Method, Boolean>>) () -> {
                    boolean hasFailed = entry.getValue().processMultiLoad();
                    return Maps.immutableEntry(entry.getKey(), hasFailed);
                }).collect(Collectors.toList());

        return executorService.invokeAll(callables)
                .stream()
                .map(this::getFutureValue)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void createTestReports(Map<Method, Boolean> methodHasFailed, Class<?> testClass) {
        reportGenerator.generateCsvReport();
//        reportGenerator.generateExtentReport();
        methodHasFailed.forEach((method, hasFailed) -> {
            if (hasFailed) {
                failTest(method, testClass);
            } else {
                LOGGER.info("\nAll Passed in test: {} and in method: {} \uD83D\uDC3C. " + "\nSee the granular " +
                        "'csv report' for individual test statistics.", testClass.getName(), method.getName());
            }
        });
    }

    private void failTest(Method testMethod, Class<?> testClass) {
        String failureMessage = testClass.getName() + " with load/stress test(s): " + testMethod + " have Failed";
        LOGGER.error("\n" + failureMessage + ". \n\uD83D\uDC47" +
                "\na) See the 'target/' for granular 'csv report' for pass/fail/response-delay statistics.\uD83D\uDE0E" +
                "\n-Also- " +
                "\nb) See the 'target/logs' for individual failures by their correlation-ID.\n\n");
        String testDescription = testClass + "#" + testMethod;

        fail(testDescription, new RuntimeException(failureMessage));
    }

    private <T> T getFutureValue(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
