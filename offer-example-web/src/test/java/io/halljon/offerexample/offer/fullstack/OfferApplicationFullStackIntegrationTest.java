package io.halljon.offerexample.offer.fullstack;

//import io.halljon.offerexample.offer.OfferApplication;

import io.halljon.offerexample.offer.OfferApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfferApplication.class, webEnvironment = RANDOM_PORT)
public class OfferApplicationFullStackIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void createNewOffer() {
        System.out.println("TODO");
    }
}
