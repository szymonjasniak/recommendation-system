package com.algoteque.recommendationsystem.quote.data;

import com.algoteque.recommendationsystem.common.data.TopicName;

import java.util.Map;
import java.util.stream.Collectors;

public record QuoteCalculationRequest(Map<TopicName, Integer> requestedTopics) {

    public QuoteCalculationRequest getTop3Topics() {
        return new QuoteCalculationRequest(requestedTopics().entrySet()
                .stream()
                .sorted(Map.Entry.<TopicName, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
