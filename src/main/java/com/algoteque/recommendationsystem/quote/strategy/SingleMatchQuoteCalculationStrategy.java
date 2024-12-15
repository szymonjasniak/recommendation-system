package com.algoteque.recommendationsystem.quote.strategy;

import com.algoteque.recommendationsystem.quote.data.MatchedTopicContext;
import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;

import java.math.BigDecimal;

public class SingleMatchQuoteCalculationStrategy implements QuoteCalculationStrategy {

    private static final BigDecimal FIRST_HIGHEST_MATCH_RATE = BigDecimal.valueOf(0.30);
    private static final BigDecimal SECOND_HIGHEST_MATCH_RATE = BigDecimal.valueOf(0.25);
    private static final BigDecimal THIRD_HIGHEST_MATCH_RATE = BigDecimal.valueOf(0.20);

    @Override
    public BigDecimal calculate(TopicMatchResult topicMatch) {
        assert topicMatch.isSingleMatch();
        return topicMatch.matchedProviderTopics()
                .values()
                .stream()
                .findFirst()
                .map(this::calculateFinalQuote)
                .orElseThrow();
    }

    private BigDecimal calculateFinalQuote(MatchedTopicContext matchedTopicContext) {
        return getMatchPercentage(matchedTopicContext.topicRank()).multiply(BigDecimal.valueOf(matchedTopicContext.requestedQuote()));
    }

    private BigDecimal getMatchPercentage(int index) {
        return switch (index) {
            case 0 -> FIRST_HIGHEST_MATCH_RATE;
            case 1 -> SECOND_HIGHEST_MATCH_RATE;
            case 2 -> THIRD_HIGHEST_MATCH_RATE;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }
}