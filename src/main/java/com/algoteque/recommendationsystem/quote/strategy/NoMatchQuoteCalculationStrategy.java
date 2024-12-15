package com.algoteque.recommendationsystem.quote.strategy;

import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;

import java.math.BigDecimal;

public class NoMatchQuoteCalculationStrategy implements QuoteCalculationStrategy {

    @Override
    public BigDecimal calculate(TopicMatchResult topicMatch) {
        assert !topicMatch.isMatched();
        return BigDecimal.ZERO;
    }
}
