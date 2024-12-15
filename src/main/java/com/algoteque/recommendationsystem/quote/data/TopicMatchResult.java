package com.algoteque.recommendationsystem.quote.data;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.common.data.TopicName;

import java.util.Map;

public record TopicMatchResult(
        ProviderName provider,
        Map<TopicName, MatchedTopicContext> matchedProviderTopics) {

    public boolean isMatched() {
        return !matchedProviderTopics.isEmpty();
    }

    public boolean isSingleMatch() {
        return matchedProviderTopics.size() == 1;
    }

    public boolean isDoubleMatch() {
        return matchedProviderTopics.size() == 2;
    }

}
