package com.algoteque.recommendationsystem.quote.strategy;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.common.data.TopicName;
import com.algoteque.recommendationsystem.quote.data.MatchedTopicContext;
import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DoubleMatchQuoteCalculationStrategyTest {

    private DoubleMatchQuoteCalculationStrategy strategy;

    @BeforeEach
    public void setup() {
        strategy = new DoubleMatchQuoteCalculationStrategy();
    }

    @Test
    public void testCalculate_ShouldReturnCorrectQuote() {
        Map<TopicName, MatchedTopicContext> topics = Map.of(
                new TopicName("economy"), new MatchedTopicContext(0, 30),
                new TopicName("history"), new MatchedTopicContext(0, 20)
        );
        TopicMatchResult topicMatchResult = new TopicMatchResult(new ProviderName("provider_a"), topics);

        BigDecimal quote = strategy.calculate(topicMatchResult);

        assertThat(quote).isEqualByComparingTo(BigDecimal.valueOf(5));
    }

    @Test
    public void testCalculate_ShouldThrowException() {
        Map<TopicName, MatchedTopicContext> topics = Map.of(
                new TopicName("economy"), new MatchedTopicContext(0, 30)
        );
        TopicMatchResult topicMatchResult = new TopicMatchResult(new ProviderName("provider_a"), topics);

        assertThrows(AssertionError.class, () -> strategy.calculate(topicMatchResult));
    }

}