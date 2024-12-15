package com.algoteque.recommendationsystem.quote;

import com.algoteque.recommendationsystem.quote.data.Quote;
import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;
import com.algoteque.recommendationsystem.quote.strategy.QuoteCalculationStrategyFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuoteCalculator {

    private final QuoteCalculationStrategyFactory quoteCalculationStrategyFactory;

    public QuoteCalculator(QuoteCalculationStrategyFactory quoteCalculationStrategyFactory) {
        this.quoteCalculationStrategyFactory = quoteCalculationStrategyFactory;
    }

    public Set<Quote> calculate(Set<TopicMatchResult> topicMatchResults) {
        return topicMatchResults
                .stream()
                .map(topicMatch -> new Quote(topicMatch.provider(), calculateQuote(topicMatch)))
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateQuote(TopicMatchResult topicMatch) {
        return quoteCalculationStrategyFactory.getStrategy(topicMatch).calculate(topicMatch);
    }
}
