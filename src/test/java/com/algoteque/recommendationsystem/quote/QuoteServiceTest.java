package com.algoteque.recommendationsystem.quote;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.common.data.TopicName;
import com.algoteque.recommendationsystem.provider.ProviderTopicConfig;
import com.algoteque.recommendationsystem.provider.data.ProviderTopics;
import com.algoteque.recommendationsystem.provider.data.Providers;
import com.algoteque.recommendationsystem.quote.data.Quote;
import com.algoteque.recommendationsystem.quote.data.QuoteCalculationRequest;
import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuoteServiceTest {

    private static final ProviderName PROVIDER_1 = new ProviderName("provider_1");
    private static final ProviderName PROVIDER_2 = new ProviderName("provider_2");
    private static final ProviderName PROVIDER_3 = new ProviderName("provider_3");
    private static final ProviderName PROVIDER_4 = new ProviderName("provider_4");
    private static final ProviderName PROVIDER_5 = new ProviderName("provider_5");

    @Captor
    ArgumentCaptor<Set<TopicMatchResult>> topicMatchResultCaptor;
    @Mock
    private ProviderTopicConfig providerTopicConfig;
    @Mock
    private QuoteCalculator quoteCalculator;
    @InjectMocks
    private QuoteService quoteService;

    @Test
    public void testCalculateQuotes_ShouldReturnQuotes() {
        QuoteCalculationRequest quoteCalculationRequest = new QuoteCalculationRequest(Map.of(
                new TopicName("reading"), 20,
                new TopicName("math"), 50,
                new TopicName("science"), 30,
                new TopicName("history"), 15,
                new TopicName("art"), 10
        ));
        when(providerTopicConfig.getProviders()).thenReturn(new Providers(Map.of(
                PROVIDER_1, createProviderTopics("math", "science"),
                PROVIDER_2, createProviderTopics("history", "science"),
                PROVIDER_3, createProviderTopics("art", "reading")
        )));
        mockQuoteCalculator();

        Set<Quote> quotes = quoteService.calculateQuotes(quoteCalculationRequest);

        assertThat(quotes).isNotEmpty();
        verify(quoteCalculator, times(1)).calculate(topicMatchResultCaptor.capture());
        Set<TopicMatchResult> topicMatchResults = topicMatchResultCaptor.getValue();

        assertThat(topicMatchResults)
                .hasSize(3)
                .extracting(TopicMatchResult::provider, topicMatchResult -> topicMatchResult.matchedProviderTopics().keySet())
                .containsOnly(
                        tuple(PROVIDER_1, Set.of(new TopicName("science"), new TopicName("math"))),
                        tuple(PROVIDER_2, Set.of(new TopicName("science"))),
                        tuple(PROVIDER_3, Set.of(new TopicName("reading")))
                );

    }

    @Test
    public void testCalculateQuotes_ShouldReturnQuotesOnlyFor3TopTopics() {
        QuoteCalculationRequest quoteCalculationRequest = new QuoteCalculationRequest(Map.of(
                new TopicName("reading"), 20,
                new TopicName("math"), 50,
                new TopicName("science"), 30,
                new TopicName("history"), 15,
                new TopicName("art"), 10
        ));
        when(providerTopicConfig.getProviders()).thenReturn(new Providers(Map.of(
                PROVIDER_1, createProviderTopics("reading"),
                PROVIDER_2, createProviderTopics("art"),
                PROVIDER_3, createProviderTopics("science"),
                PROVIDER_4, createProviderTopics("history"),
                PROVIDER_5, createProviderTopics("math")
        )));
        mockQuoteCalculator();

        Set<Quote> quotes = quoteService.calculateQuotes(quoteCalculationRequest);

        assertThat(quotes).isNotEmpty();
        verify(quoteCalculator, times(1)).calculate(topicMatchResultCaptor.capture());
        Set<TopicMatchResult> topicMatchResults = topicMatchResultCaptor.getValue();

        assertThat(topicMatchResults)
                .hasSize(3)
                .extracting(TopicMatchResult::provider, topicMatchResult -> topicMatchResult.matchedProviderTopics().keySet())
                .containsOnly(
                        tuple(PROVIDER_1, Set.of(new TopicName("reading"))),
                        tuple(PROVIDER_3, Set.of(new TopicName("science"))),
                        tuple(PROVIDER_5, Set.of(new TopicName("math")))
                );

    }

    @Test
    public void testCalculateQuotes_ShouldReturnQuotesForSimpleRequest() {
        QuoteCalculationRequest quoteCalculationRequest = new QuoteCalculationRequest(Map.of(
                new TopicName("reading"), 20
        ));
        when(providerTopicConfig.getProviders()).thenReturn(new Providers(Map.of(
                PROVIDER_1, createProviderTopics("math", "science"),
                PROVIDER_2, createProviderTopics("history", "science"),
                PROVIDER_3, createProviderTopics("art", "reading")
        )));
        mockQuoteCalculator();

        Set<Quote> quotes = quoteService.calculateQuotes(quoteCalculationRequest);

        assertThat(quotes).isNotEmpty();
        verify(quoteCalculator, times(1)).calculate(topicMatchResultCaptor.capture());
        Set<TopicMatchResult> topicMatchResults = topicMatchResultCaptor.getValue();

        assertThat(topicMatchResults)
                .hasSize(1)
                .extracting(TopicMatchResult::provider, topicMatchResult -> topicMatchResult.matchedProviderTopics().keySet())
                .containsOnly(
                        tuple(PROVIDER_3, Set.of(new TopicName("reading"))));

    }

    @Test
    public void testCalculateQuotes_ShouldReturnQuotesWhenNoProviders() {
        QuoteCalculationRequest quoteCalculationRequest = new QuoteCalculationRequest(Map.of(new TopicName("reading"), 20));
        when(providerTopicConfig.getProviders()).thenReturn(new Providers(Map.of()));

        Set<Quote> quotes = quoteService.calculateQuotes(quoteCalculationRequest);

        assertThat(quotes).isEmpty();
        verifyNoInteractions(quoteCalculator);

    }

    private ProviderTopics createProviderTopics(String name1) {
        return new ProviderTopics(List.of(new TopicName(name1)));
    }

    private ProviderTopics createProviderTopics(String name1, String name2) {
        return new ProviderTopics(List.of(new TopicName(name1), new TopicName(name2)));
    }

    private void mockQuoteCalculator() {
        when(quoteCalculator.calculate(any())).thenAnswer(invocation -> {
            Set<TopicMatchResult> results = invocation.getArgument(0);
            return results.stream().map(topicMatchResult -> new Quote(topicMatchResult.provider(), BigDecimal.TEN)).collect(Collectors.toSet());
        });
    }

}
