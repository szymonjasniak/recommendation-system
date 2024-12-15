package com.algoteque.recommendationsystem.provider.data;

import com.algoteque.recommendationsystem.common.data.ProviderName;

import java.util.Map;

public record Providers(Map<ProviderName, ProviderTopics> providersSetup) {
}
