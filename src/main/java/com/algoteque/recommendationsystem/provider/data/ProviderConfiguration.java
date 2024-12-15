package com.algoteque.recommendationsystem.provider.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ProviderConfiguration(
        @JsonProperty("provider_topics") Map<String, String> providerTopics) {
}
