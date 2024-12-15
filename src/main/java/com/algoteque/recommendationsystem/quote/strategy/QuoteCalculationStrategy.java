package com.algoteque.recommendationsystem.quote.strategy;

import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;

import java.math.BigDecimal;

public interface QuoteCalculationStrategy {

    BigDecimal calculate(TopicMatchResult topicMatch);
}
