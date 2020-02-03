package com.war.orke.crusader;

import com.war.orke.application.H2EmbeddedSpringApplication;
import com.war.orke.dto.ColonyDto;
import com.war.orke.framework.AbstractDbunitTransactionalJUnit4SpringContextTests;
import com.war.orke.framework.DbunitDataSets;
import com.war.orke.service.ColonyService;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigInteger;

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

    @Test
    @DbunitDataSets(
            before = "initialization/colony/adding-new-colony.xml",
            after = "expected/colony/adding-new-colony.xml"
    )
    public void testAddingColony() {
        colonyService.addingNewColony(getColonyDto());
    }

    @Test
    @DbunitDataSets(
            before = "initialization/colony/deleting-colony.xml",
            after = "expected/colony/deleting-colony.xml"
    )
    public void testDeletingColony() {
        colonyService.deleteColony(MOSCOW);
    }

    private ColonyDto getColonyDto() {
        ColonyDto colonyDto = new ColonyDto();
        colonyDto.setId(1);
        colonyDto.setName("Yalta");
        colonyDto.setPopulationCount(BigInteger.valueOf(670));
        return colonyDto;
    }
}