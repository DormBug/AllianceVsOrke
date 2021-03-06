package com.war.orke.colony.scenario;

import com.war.orke.runner.ZerocodeSpringBootRunner;
import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.jsmart.zerocode.core.domain.UseHttpClient;
import org.jsmart.zerocode.core.httpclient.ssl.SslTrustHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;

@TargetEnv("colony/application_host.properties")
@UseHttpClient(SslTrustHttpClient.class)
@RunWith(ZerocodeSpringBootRunner.class)
public class OrkeColonyOperationsTest {

    @Test
    @JsonTestCase("colony/scenario/check_adding_colony.json")
    public void checkAddingColony() {
    }

    @Test
    @JsonTestCase("colony/scenario/check_invalid_request.json")
    public void checkInvalidRequest() {
    }

    @Test
    @JsonTestCase("colony/scenario/check_colony_adding_conflict.json")
    public void checkColonyAddingConflict() {
    }

}
