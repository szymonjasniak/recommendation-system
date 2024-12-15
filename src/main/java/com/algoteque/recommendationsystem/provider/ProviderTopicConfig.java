package com.algoteque.recommendationsystem.provider;

import com.algoteque.recommendationsystem.provider.data.Providers;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProviderTopicConfig {

    private final Providers providers;

    public ProviderTopicConfig(ProviderTopicLoader providerTopicLoader) throws IOException {
        this.providers = providerTopicLoader.loadProvidersConfig();
    }

    public Providers getProviders() {
        return providers;
    }
}
