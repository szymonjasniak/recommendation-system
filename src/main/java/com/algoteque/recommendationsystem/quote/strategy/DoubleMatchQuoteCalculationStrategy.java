package com.algoteque.recommendationsystem.quote.strategy;

import com.algoteque.recommendationsystem.quote.data.MatchedTopicContext;
import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;

import java.math.BigDecimal;

public class DoubleMatchQuoteCalculationStrategy implements QuoteCalculationStrategy {

    private static final BigDecimal RATE = BigDecimal.valueOf(0.1);

    @Override
    public BigDecimal calculate(TopicMatchResult topicMatch) {
        assert topicMatch.isDoubleMatch();
        return RATE.multiply(BigDecimal.valueOf(matchedQuote(topicMatch)));
    }

    private int matchedQuote(TopicMatchResult topicMatch) {
        return topicMatch
                .matchedProviderTopics()
                .values()
                .stream()
                .map(MatchedTopicContext::requestedQuote)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
