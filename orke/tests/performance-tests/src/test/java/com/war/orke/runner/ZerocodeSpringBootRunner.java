package com.war.orke.runner;

import com.war.orke.application.H2EmbeddedSpringApplication;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.runners.model.InitializationError;

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