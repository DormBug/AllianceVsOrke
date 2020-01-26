package com.war.alliance.crusader;

import com.war.alliance.document.Crusader;
import com.war.alliance.repository.CrusaderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrusaderIT {

    private static final String CRUSADER_NAME = "Yellow";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CrusaderRepository crusaderRepository;

    @Test
    public void testAddingCrusader() {
        Crusader crusader = new Crusader();
        crusader.setName(CRUSADER_NAME);

        webTestClient.post().uri("/alliance/crusader")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(crusader), Crusader.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Yellow");
    }

    @Test
    public void testGettingCrusaders() {
        Crusader crusader = new Crusader();
        crusader.setName(CRUSADER_NAME);

        crusaderRepository.save(crusader);

        webTestClient.get().uri("/alliance/crusader")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Crusader.class)
                .hasSize(1);
    }
}