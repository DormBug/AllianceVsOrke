# AllianceVsOrke

This project is only structure for your new greatest projects - it's like a template, which works by Spring Boot. I use Gradle for building my template, but it can be written by using Maven. There are minimum business logic, because, frequently, it is will be, when will be requirements.  

This template easy to modify, expand and now I will show you, what this template already have and how it all works.

## Dependency managment 

When project contains only one main module - we don't need dependency managment, because we can keep all our dependencies in build.gradle file and they are will be available for all project.

But, in this template we have 2 main modules - 'orke' and 'alliance' and each module have 5+ sub-modules and etc.
For this situation in folder gradle we have dependencies.gradle file, which contains all our dependencies:

```
apply plugin: 'io.spring.dependency-management'

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

dependencyManagement {
    // https://github.com/spring-gradle-plugins/dependency-management-plugin/issues/222
    applyMavenExclusions = false
    dependencies {

        // 1. org.springframework.cloud:spring-cloud-starter-consul-discovery:2.1.1

        dependency 'org.springframework.cloud:spring-cloud-starter-consul:2.1.1.RELEASE'
        dependency 'org.springframework.cloud:spring-cloud-consul-discovery:2.1.1.RELEASE'
        dependency 'org.springframework.cloud:spring-cloud-netflix-core:2.1.1.RELEASE'
        dependency 'org.springframework.cloud:spring-cloud-starter-netflix-ribbon:2.1.1.RELEASE'
        dependency 'joda-time:joda-time:2.10.1'
```

In our main build.gradle file we made available these dependencies for all our sub-modules:

```
plugins {
	id 'io.spring.dependency-management' version '1.0.6.RELEASE' apply false
}

def javaProjects() { return subprojects.findAll { it.file("build.gradle").exists() } }

configure(javaProjects()) {
	apply plugin: 'java'
	apply from: "$rootProject.projectDir/gradle/dependencies.gradle"
```
So, now we can add dependency to our sub-module by writting it's group id and name without version, because in our dependency managment can be only one version with equals group id and name:

```
description=':orke:main'

dependencies {
    compile 'org.hibernate:hibernate-core'
    compile 'org.springframework.boot:spring-boot-starter-web'
    compile 'org.springframework.data:spring-data-jpa'
```

## Module Orke

### Database

I chosen MariaDB SQL database for this 'Orke' module - so all my database properties are in application.properties file in sub-module 'main'.

Database, jdbcTemplates, TransactionManager and etc. are in CommonDbConfig class in sub-module 'common-config'.

```
/**
 * Main config, that create connection to database by properties in application.properties file
 * and also create beans for run sql queries not using JPA - {@link JdbcTemplate}, {@link NamedParameterJdbcTemplate}
 * Includes support for transactions.
 *
 * @see JdbcTemplate
 * @see NamedParameterJdbcTemplate
 * */
@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:hibernate.properties"})
public class CommonDbConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Autowired
    private Environment environment;

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(hibernateProperties());
        factory.setPackagesToScan("com.war.orke");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
```
Also, I have available to use not only JPA, but NamedParameterJdbcTemplate and I keep queries in .sql files. In JDBC repositories I have fields, annotated by @InjectSQLQuery - when spring context will be run, InjectSQLQueryAnnotationProcessor will fill these fields by query from .sql file.

```
/**
 * This class process beans, which have fields annotated by {@link InjectSQLQuery} with path
 * to .sql file with query
 *
 * @see InjectSQLQuery
 * */
public class InjectSQLQueryAnnotationProcessor implements BeanPostProcessor {

```
```
@Repository
public class ColonyJdbcRepository {

    @InjectSQLQuery("com/war/orke/jdbcRepository/updateColonyInfo.sql")
    private String updateColonyInfo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void updateColonyInfo(String colonyName, BigInteger population) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("name", colonyName)
                .addValue("populationCount", population);
        jdbcTemplate.update(updateColonyInfo, source);
    }
```

### Liquibase

This template also has 'db-migration' sub-module for using Liquibase. By adding plugin, we can using it in our project.

```
description=':orke:db-migration'

apply plugin: "org.liquibase.gradle"

buildscript {
    repositories {
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath "org.liquibase:liquibase-gradle-plugin:2.0.2"
    }
}

liquibase {
    activities {
        main {
            changeLogFile 'src/main/resources/db/сhangelog-master.xml'
            url 'jdbc:mariadb://localhost:3307/orke'
            referenceUrl 'jdbc:mariadb://localhost:3307/orke'
            username 'root'
            password 'root'
        }
    }
}

dependencies {
    liquibaseRuntime 'org.liquibase:liquibase-core'
    liquibaseRuntime 'org.liquibase:liquibase-groovy-dsl'
    liquibaseRuntime 'org.mariadb.jdbc:mariadb-java-client'
}
```

### AOP

Aspects also can be there, but for using them - we need add @EnableAspectJAutoProxy to our main class. There is aspect DtoValidatorAspect:

```
/**
 * By using this aspect we can annotate some object or collection of objects by {@link DtoValidator}
 * and add in value our custom validator class, which implemented {@link org.springframework.validation.Validator}.
 * It very useful to control returned object from one API to another and write checks by object's values not in
 * business logic, but in another place named 'validation'.
 *
 * In our project perfect place for this annotating is controllers.
 *
 * @see DtoValidator
 * @see org.springframework.validation.Validator
 * */
@Aspect
@Component
public class DtoValidatorAspect {

    @Autowired
    private ValidatorService validatorService;

    @Around("execution(* com.war.orke.controller..*.*(.., @com.war.orke.validator.DtoValidator (*),..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
```

Our ColonyController method:

```
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addingNewColony(@RequestBody @Valid @DtoValidator(ColonyValidator.class) ColonyDto colonyDto) {
        colonyService.addingNewColony(colonyDto);
    }
```
And ColonyValidator:

```
package com.war.orke.validator;

import com.war.orke.dto.ColonyDto;
import com.war.orke.dto.ErrorData;
import com.war.orke.exception.ValidationException;
import com.war.orke.repository.ColonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ColonyValidator implements Validator {

    private static final String FIELD_NAME = "name";
    private static final String EXISTING_COLONY_NAME_ERROR_MESSAGE = "Colony with name '%s' is already created";

    @Autowired
    private ColonyRepository colonyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ColonyDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ColonyDto colony = (ColonyDto) target;
        // some business logic
        String name = colony.getName();
        if (colonyRepository.findByName(name).isPresent()) {
            throw new ValidationException(
                    new ErrorData(FIELD_NAME, String.format(EXISTING_COLONY_NAME_ERROR_MESSAGE, name)));
        }
    }
}
```

### JUnit 5 and Mockito Testing

In sub-module 'colony-service' we have business logic for checking, adding, udpating and deleting data in database. By JUnit 5 and Mockito in test class ColonyServiceTest we covered service layer by tests.
```
@ExtendWith(MockitoExtension.class)
public class ColonyServiceTest {
```
```
    @Test
    public void testGettingOrkeColonies() {
        when(colonyRepository.findAll()).thenReturn(getColonyIterable());
        when(colonyMapper.toOrkeColonyDto(any(Colony.class)))
                .thenReturn(getColonyDto());

        List<ColonyDto> resultList = service.getOrkeColonies();

        assertAll(
                () -> assertEquals(3, resultList.size()),
                () -> assertEquals(getExpectedColonyListDto(), resultList));
    }
```
### DBUnit (Database data testing)

In sub-module 'db2db-tests', I test database data after business logic running using DBUnit for that. Main test class is AbstractDbunitTransactionalJUnit4SpringContextTests:

```
/**
 * Main class, that must be extended by test class, which want to use DBUnit for db2db testing.
 *
 * Test method must have annotating {@link DbunitDataSets} with 'before' and 'after' properties:
 *
 * 1) 'before' - path to xml file, which contains tables and data in it, that will be initialized before test running.
 *      Method 'beforeTestMethod' in {@link DbunitTestExecutionListener} process that.
 * 2) 'after' = path to xml file, which contains tables and data in it, that expected after test running
 *      Method 'assertAfterTransaction' in {@link AbstractDbunitTransactionalJUnit4SpringContextTests}
 *      compares actual data after test running and expected data, which is in xml file by path by property 'after'.
 * Also, Test class must be annotated by @Rollback(false), or test running will be ignored.
 *
 * @see DbunitDataSets
 * @see DbunitTestExecutionListener
 * @see AbstractDbunitTransactionalJUnit4SpringContextTests
 * */
@TestExecutionListeners(AbstractDbunitTransactionalJUnit4SpringContextTests.DbunitTestExecutionListener.class)
public abstract class AbstractDbunitTransactionalJUnit4SpringContextTests
        extends AbstractTransactionalJUnit4SpringContextTests {
```
And simple test which using AbstractDbunitTransactionalJUnit4SpringContextTests class:

```
/**
 * Run application with in-memory database H2 by using {@link H2EmbeddedSpringApplication}
 * to be closer to real project
 *
 * @see H2EmbeddedSpringApplication
 * */
@Rollback(false)
@SpringBootTest(classes = H2EmbeddedSpringApplication.class)
@TestMethodOrder(value = MethodOrderer.Alphanumeric.class)
public class ColonyServiceDbTest extends AbstractDbunitTransactionalJUnit4SpringContextTests {

    private static final String MOSCOW = "Moscow";

    @Autowired
    private ColonyService colonyService;

    @Test
    @DbunitDataSets(
            before = "initialization/colony/updating-colony-info.xml",
            after = "expected/colony/updating-colony-info.xml"
    )
    public void testUpdatingColonyInfo() {
        colonyService.updateColonyInfo(MOSCOW, BigInteger.valueOf(450));
    }
```

### Zerocode (Performance Testing)

Also, I added performance testing to control performance, while project will be expanded. Db2Db and Performance testing are very important for new big project, I think. Thanks them, you, developer, will be sure, that your code work exactly, how you want for every stage of development.
Main runner, that run tests in parallel in sub-module 'performance-tests': 
```
/**
 * This runner was created for optimize performance by running tests not in sequence but in parallel.
 * By this class we can easy create 100 and more requests and watch, how many time our project will be process them
 * and also it in twice quicker than run tests in sequence (but tests mustn't interact with each other).
 *
 * */
public class SpringApplicationParallelRunner implements BeforeAllCallback {
```
And simple test, which run 3 parallel scenarious:
```
@LoadWith("colony/load_generation.properties")
@ExtendWith({SpringApplicationParallelRunner.class})
public class OrkeColonyLoadTest {

    @Test
    @DisplayName("Colony Operations Test")
    @TestMappings({
            @TestMapping(testClass = OrkeColonyOperationsTest.class, testMethod = "checkAddingColony"),
            @TestMapping(testClass = OrkeColonyOperationsTest.class, testMethod = "checkInvalidRequest"),
            @TestMapping(testClass = OrkeColonyOperationsTest.class, testMethod = "checkColonyAddingConflict")
    })
    public void colonyOperationTests() {
    }
```

### Main
```
/**
 * This is main class for module 'orke' that deploys on port and with using MariaDB, which properties are
 * in application.properties. {@link EnableAspectJAutoProxy} using there for including AOP. Now we have main
 * aspect com.war.orke.aspect.DtoValidatorAspect
 * */
@SpringBootApplication(scanBasePackages = {"com.war.orke"})
@EntityScan(basePackages = {"com.war.orke.entity"})
@EnableJpaRepositories(basePackages = {"com.war.orke.repository"})
@EnableAspectJAutoProxy
@EnableScheduling
public class OrkeApplication {

    private static final Logger LOG = LoggerFactory.getLogger(OrkeApplication.class);

    public static void main(String[] args) {
        LOG.info("Orke Application starts...");
        start();
    }

    public static void start() {
        SpringApplication.run(OrkeApplication.class);
    }

}
```

## Module Alliance

### Webflux

This module written in react, so there we using Spring Webflux - work with Flux and Mono objects: 

```
@RestController
@RequestMapping("/alliance/crusader")
public class CrusaderController {

    @Autowired
    private CrusaderService service;

    @GetMapping
    public Flux<Crusader> getCrusaderList() {
        return service.getAllCrusaders();
    }

    @PostMapping
    public Mono<Crusader> addCrusader(@RequestBody Crusader crusader) {
        return service.addCrusader(crusader);
    }
}
```
### Database
For this module, I chosen MongoDB NoSQL database. Spring Boot auto configure MongoDB, so we need only add mongoDb properties in application.properties;
And for mapping, we using @Document on our class mapping.
```
@Document("crusader")
public class Crusader {

    private ObjectId id;

    private String name;
```
### Rective MongoDB
For using rective MongoDB I replace my 'CrudMongoRepository' to 'RectiveMongoRepository' and returned objects to Flux an Mono of them.
```
@Repository
public interface CrusaderRepository extends ReactiveMongoRepository<Crusader, ObjectId> {
}
```
```
@Service
public class CrusaderServiceImpl implements CrusaderService {

    @Autowired
    private CrusaderRepository repository;

    @Override
    public Flux<Crusader> getAllCrusaders() {
        return repository.findAll();
    }
```
### Main
```
/**
 * Main class, that deploy module 'alliance' on port and with using MongoDB, which properties are
 * in application.properties
 *  */
@SpringBootApplication(scanBasePackages = {"com.war.alliance"})
@EnableReactiveMongoRepositories(basePackages = "com.war.alliance.repository")
public class AllianceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllianceApplication.class, args);
    }
}
```
## Conclusions

That's all, you can run 'orke' and 'alliance' modules, create requests from browser or Postman and make sure, that all works perfectly. Also, you can run tests - they are also work.
You can use this template for quick start, if you want. 

Have a nice day!
