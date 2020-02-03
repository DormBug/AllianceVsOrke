package com.war.orke.runner;

import com.war.orke.application.H2EmbeddedSpringApplication;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.runners.model.InitializationError;

/**
 * This simple runner equals to {@link ZeroCodeUnitRunner},
 * but it runs context by {@link H2EmbeddedSpringApplication} to be closer to real project.
 *
 * @see ZeroCodeUnitRunner
 * @see H2EmbeddedSpringApplication
 * */
public class ZerocodeSpringBootRunner extends ZeroCodeUnitRunner {
    public static boolean appRunning = false;

    public ZerocodeSpringBootRunner(Class<?> klass) throws InitializationError {
        super(klass);
        init();
    }

    private static synchronized void init() {
        if(!appRunning){
            H2EmbeddedSpringApplication.start();
            appRunning = true;
        }
    }
}