package com.algoteque.recommendationsystem.quote;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.common.data.TopicName;
import com.algoteque.recommendationsystem.quote.data.MatchedTopicContext;
import com.algoteque.recommendationsystem.quote.data.Quote;
import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;
import com.algoteque.recommendationsystem.quote.strategy.QuoteCalculationStrategy;
import com.algoteque.recommendationsystem.quote.strategy.QuoteCalculationStrategyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuoteCalculatorTest {

    private static final ProviderName PROVIDER_NAME = new ProviderName("provider_a");
    private static final BigDecimal QUOTE_VALUE = BigDecimal.valueOf(3);
    @Mock
    private QuoteCalculationStrategyFactory quoteCalculationStrategyFactory;
    @Mock
    private QuoteCalculationStrategy quoteCalculationStrategy;
    @InjectMocks
    private QuoteCalculator quoteCalculator;

    @Test
    public void testCalculate_ShouldReturnQuotes() {
        TopicMatchResult topicMatchResult = new TopicMatchResult(PROVIDER_NAME,
                Map.of(new TopicName("economy"), new MatchedTopicContext(1, 30)));

        when(quoteCalculationStrategyFactory.getStrategy(topicMatchResult)).thenReturn(quoteCalculationStrategy);
        when(quoteCalculationStrategy.calculate(any())).thenReturn(QUOTE_VALUE);

        Set<Quote> quotes = quoteCalculator.calculate(Set.of(topicMatchResult));

        assertThat(quotes).hasSize(1);
        Quote quote = quotes.iterator().next();
        assertThat(quote.provider()).isEqualTo(PROVIDER_NAME);
        assertThat(quote.quote()).isEqualByComparingTo(QUOTE_VALUE);

        verify(quoteCalculationStrategy, times(1)).calculate(topicMatchResult);
    }

    @Test
    public void testCalculate_WithEmptyTopicMatchResults_ShouldReturnEmptySet() {
        Set<TopicMatchResult> emptyTopicMatchResults = new HashSet<>();

        Set<Quote> quotes = quoteCalculator.calculate(emptyTopicMatchResults);

        assertThat(quotes).isEmpty();
        verifyNoInteractions(quoteCalculationStrategy, quoteCalculationStrategyFactory);
    }

}
