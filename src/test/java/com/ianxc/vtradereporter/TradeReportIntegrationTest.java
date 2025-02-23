package com.ianxc.vtradereporter;

import com.ianxc.vtradereporter.model.api.Trade;
import com.ianxc.vtradereporter.model.api.TradeSubmission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(statements = {
        "TRUNCATE TABLE trade;",
        "ALTER SEQUENCE trade_seq RESTART WITH 1;"
})
class TradeReportIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void when_submitBundledTradesAndGetFiltered_then_returnsMatchingTrades() {
        // Submit the bundled trades
        final var submissionResponse = restTemplate.postForEntity("/trades/submit/bundled", null, TradeSubmission.class);

        assertThat(submissionResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(submissionResponse.getBody()).isNotNull();
        assertThat(submissionResponse.getBody().count()).isPositive();

        // Then get filtered trades
        final var listTradeTypeRef = new ParameterizedTypeReference<List<Trade>>() {
        };
        final var filteredResponse = restTemplate.exchange("/trades/prefiltered?kind=CHALLENGE", HttpMethod.GET, null, listTradeTypeRef);

        assertThat(filteredResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(filteredResponse.getBody()).isNotNull();

        // Verify filtered results
        final var trades = filteredResponse.getBody();
        assertThat(trades).isNotEmpty();
        assertThat(trades).allSatisfy(trade -> {
            final var opt1 = "EMU_BANK".equals(trade.sellerParty()) && "AUD".equals(trade.premiumCurrency());
            final var opt2 = "BISON_BANK".equals(trade.sellerParty()) && "USD".equals(trade.premiumCurrency());
            assertThat(opt1 || opt2).isTrue();
            assertThat(areAnagramsAlt(trade.buyerParty(), trade.sellerParty())).isFalse();
        });
    }

    // An alternative implementation of anagrams
    private boolean areAnagramsAlt(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        final var chars1 = s1.chars().sorted().toArray();
        final var chars2 = s2.chars().sorted().toArray();
        return Arrays.equals(chars1, chars2);
    }
}
