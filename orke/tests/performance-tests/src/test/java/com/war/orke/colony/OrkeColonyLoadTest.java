package com.war.orke.colony;

import com.war.orke.colony.scenario.OrkeColonyOperationsTest;
import com.war.orke.runner.SpringApplicationParallelRunner;
import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.domain.TestMappings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
}
