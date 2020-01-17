package com.war.orke.colony;

import com.war.orke.colony.scenario.OrkeColonyOperationsTest;
import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.runner.parallel.ZeroCodeLoadRunner;
import org.junit.runner.RunWith;

@LoadWith("colony/load_generation.properties")
@TestMapping(testClass = OrkeColonyOperationsTest.class, testMethod = "checkAddingColony")
@RunWith(ZeroCodeLoadRunner.class)
//@ExtendWith({SpringApplicationParallelRunner.class})
public class OrkeColonyLoadTest {

}
