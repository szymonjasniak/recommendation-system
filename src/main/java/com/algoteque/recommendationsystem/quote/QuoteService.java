package com.algoteque.recommendationsystem.quote;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.common.data.TopicName;
import com.algoteque.recommendationsystem.provider.ProviderTopicConfig;
import com.algoteque.recommendationsystem.provider.data.ProviderTopics;
import com.algoteque.recommendationsystem.quote.data.MatchedTopicContext;
import com.algoteque.recommendationsystem.quote.data.Quote;
import com.algoteque.recommendationsystem.quote.data.QuoteCalculationRequest;
import com.algoteque.recommendationsystem.quote.data.TopicMatchResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final ProviderTopicConfig providerTopicConfig;
    private final QuoteCalculator quoteCalculator;

    public QuoteService(ProviderTopicConfig providerTopicConfig, QuoteCalculator quoteCalculator) {
        this.providerTopicConfig = providerTopicConfig;
        this.quoteCalculator = quoteCalculator;
    }

    public Set<Quote> calculateQuotes(QuoteCalculationRequest quoteCalculationRequest) {
        QuoteCalculationRequest top3RequestedTopics = quoteCalculationRequest.getTop3Topics();
        Set<TopicMatchResult> topicMatchResults = findMatches(top3RequestedTopics.requestedTopics());
        return !topicMatchResults.isEmpty() ? quoteCalculator.calculate(topicMatchResults) : Collections.emptySet();
    }

    private Set<TopicMatchResult> findMatches(Map<TopicName, Integer> top3RequestedTopics) {
        return providerTopicConfig.getProviders()
                .providersSetup()
                .entrySet()
                .stream()
                .map(providerTopicEntry -> new TopicMatchResult(providerTopicEntry.getKey(), findMatchedTopics(providerTopicEntry, top3RequestedTopics)))
                .filter(TopicMatchResult::isMatched)
                .collect(Collectors.toSet());
    }

    private Map<TopicName, MatchedTopicContext> findMatchedTopics(
            Entry<ProviderName, ProviderTopics> providerTopicEntry,
            Map<TopicName, Integer> top3RequestedTopics) {
        Map<TopicName, MatchedTopicContext> matchedProviderTopics = new HashMap<>();

        top3RequestedTopics.forEach((key, value) -> {
            int rank = providerTopicEntry.getValue().getRank(key);
            if (rank >= 0) {
                matchedProviderTopics.put(key, new MatchedTopicContext(rank, value));
            }
        });
        return matchedProviderTopics;
    }


}
