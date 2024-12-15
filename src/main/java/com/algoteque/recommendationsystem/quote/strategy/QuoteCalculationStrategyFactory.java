package com.algoteque.recommendationsystem.quote.strategy;

import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;
import org.springframework.stereotype.Component;

@Component
public class QuoteCalculationStrategyFactory {

    public QuoteCalculationStrategy getStrategy(TopicMatchResult topicMatch) {
        if (!topicMatch.isMatched()) {
            return new NoMatchQuoteCalculationStrategy();
        } else if (topicMatch.isDoubleMatch()) {
            return new DoubleMatchQuoteCalculationStrategy();
        } else if (topicMatch.isSingleMatch()) {
            return new SingleMatchQuoteCalculationStrategy();
        } else {
            throw new UnsupportedOperationException("Too much topic matches");
        }
    }
}
