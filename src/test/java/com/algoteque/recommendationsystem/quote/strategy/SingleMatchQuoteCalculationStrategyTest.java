package com.algoteque.recommendationsystem.quote.strategy;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.common.data.TopicName;
import com.algoteque.recommendationsystem.quote.data.MatchedTopicContext;
import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleMatchQuoteCalculationStrategyTest {

    private SingleMatchQuoteCalculationStrategy strategy;

    public static Stream<Arguments> matchedTopicContexts() {
        return Stream.of(
                Arguments.of(0, 30, BigDecimal.valueOf(9)),
                Arguments.of(1, 30, BigDecimal.valueOf(7.5)),
                Arguments.of(2, 30, BigDecimal.valueOf(6))
        );
    }

    @BeforeEach
    public void setup() {
        strategy = new SingleMatchQuoteCalculationStrategy();
    }

    @ParameterizedTest
    @MethodSource("matchedTopicContexts")
    public void testCalculate_ShouldReturnCorrectQuote(Integer topicRank, Integer requestQuote, BigDecimal result) {
        Map<TopicName, MatchedTopicContext> topics = Map.of(
                new TopicName("economy"), new MatchedTopicContext(topicRank, requestQuote)
        );
        TopicMatchResult topicMatchResult = new TopicMatchResult(new ProviderName("provider_a"), topics);

        BigDecimal quote = strategy.calculate(topicMatchResult);

        assertThat(quote).isEqualByComparingTo(result);
    }

    @Test
    public void testCalculate_ShouldThrowExceptionWhenPreconditionNotMet() {
        Map<TopicName, MatchedTopicContext> topics = Map.of();
        TopicMatchResult topicMatchResult = new TopicMatchResult(new ProviderName("provider_a"), topics);

        assertThrows(AssertionError.class, () -> strategy.calculate(topicMatchResult));
    }
}