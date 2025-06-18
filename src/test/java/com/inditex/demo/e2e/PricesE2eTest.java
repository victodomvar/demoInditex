package com.inditex.demo.e2e;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PricesE2eTest {

    @Karate.Test
    Karate runPricesTest() {
        return Karate.run("classpath:e2e/prices.feature")
                .systemProperty("baseUrl", "http://localhost:8080")
                .outputCucumberJson(true)
                .reportDir("target/karate-reports");
    }
}
