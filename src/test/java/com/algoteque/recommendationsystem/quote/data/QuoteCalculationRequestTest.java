package com.algoteque.recommendationsystem.quote.data;

import com.algoteque.recommendationsystem.common.data.TopicName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class QuoteCalculationRequestTest {

    @Test
    void testGetTop3Topics_ShouldReturnCorrectTopics() {
        QuoteCalculationRequest quoteCalculationRequest = new QuoteCalculationRequest(Map.of(
                new TopicName("reading"), 20,
                new TopicName("math"), 50,
                new TopicName("science"), 30,
                new TopicName("history"), 15,
                new TopicName("art"), 21
        ));

        QuoteCalculationRequest top3Topics = quoteCalculationRequest.getTop3Topics();

        assertThat(top3Topics.requestedTopics())
                .hasSize(3)
                .containsOnlyKeys(new TopicName("math"), new TopicName("science"), new TopicName("art"));
    }


    @Test
    void testGetTop3Topics_ShouldReturnUpTo3Topics() {
        QuoteCalculationRequest quoteCalculationRequest = new QuoteCalculationRequest(Map.of(
                new TopicName("reading"), 20,
                new TopicName("history"), 15
        ));

        QuoteCalculationRequest top3Topics = quoteCalculationRequest.getTop3Topics();

        assertThat(top3Topics.requestedTopics())
                .hasSize(2)
                .containsOnlyKeys(new TopicName("reading"), new TopicName("history"));
    }
}