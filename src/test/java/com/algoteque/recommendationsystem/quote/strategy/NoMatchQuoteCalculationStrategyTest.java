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

class NoMatchQuoteCalculationStrategyTest {

    private NoMatchQuoteCalculationStrategy strategy;

    @BeforeEach
    public void setup() {
        strategy = new NoMatchQuoteCalculationStrategy();
    }

    @Test
    public void testCalculate_ShouldReturnZero() {
        Map<TopicName, MatchedTopicContext> topics = Map.of();
        TopicMatchResult topicMatchResult = new TopicMatchResult(new ProviderName("provider_a"), topics);

        BigDecimal quote = strategy.calculate(topicMatchResult);

        assertThat(quote).isEqualByComparingTo(BigDecimal.ZERO);
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