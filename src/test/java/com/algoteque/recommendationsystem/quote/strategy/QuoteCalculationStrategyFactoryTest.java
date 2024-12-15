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

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuoteCalculationStrategyFactoryTest {

    private QuoteCalculationStrategyFactory quoteCalculationStrategyFactory;

    public static Stream<Arguments> matchedTopicContexts() {
        return Stream.of(
                Arguments.of(1, SingleMatchQuoteCalculationStrategy.class),
                Arguments.of(2, DoubleMatchQuoteCalculationStrategy.class),
                Arguments.of(0, NoMatchQuoteCalculationStrategy.class)
        );
    }

    @BeforeEach
    public void setup() {
        quoteCalculationStrategyFactory = new QuoteCalculationStrategyFactory();
    }

    @ParameterizedTest
    @MethodSource("matchedTopicContexts")
    void testGetStrategy_ShouldReturnCorrectStrategy(Integer matchedTopics, Class<?> strategyClass) {
        Map<TopicName, MatchedTopicContext> topics = IntStream.range(0, matchedTopics)
                .boxed()
                .collect(Collectors.toMap(
                        topicSuffix -> new TopicName("economy" + topicSuffix),
                        topicSuffix -> new MatchedTopicContext(0, 30)));
        TopicMatchResult topicMatchResult = new TopicMatchResult(new ProviderName("provider_a"), topics);

        QuoteCalculationStrategy strategy = quoteCalculationStrategyFactory.getStrategy(topicMatchResult);

        assertThat(strategy).isInstanceOf(strategyClass);
    }


    @Test
    public void testCalculate_ShouldThrowException() {
        Map<TopicName, MatchedTopicContext> topics = Map.of(
                new TopicName("economy"), new MatchedTopicContext(0, 30),
                new TopicName("history"), new MatchedTopicContext(0, 30),
                new TopicName("math"), new MatchedTopicContext(0, 30),
                new TopicName("it"), new MatchedTopicContext(0, 30)
        );
        TopicMatchResult topicMatchResult = new TopicMatchResult(new ProviderName("provider_a"), topics);

        assertThrows(UnsupportedOperationException.class, () -> quoteCalculationStrategyFactory.getStrategy(topicMatchResult));
    }
}