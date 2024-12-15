package com.algoteque.recommendationsystem.provider.data;

import com.algoteque.recommendationsystem.common.data.TopicName;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class ProviderTopics {

    private static final int NOT_MATCHED_INDEX = -1;

    private final Map<TopicName, Integer> topics;

    public ProviderTopics(List<TopicName> topics) {
        assert topics != null && topics.size() <= 3;
        this.topics = topics.stream().collect(Collectors.toMap(identity(), topics::indexOf));
    }

    public int getRank(TopicName key) {
        return topics.getOrDefault(key, NOT_MATCHED_INDEX);
    }
}
