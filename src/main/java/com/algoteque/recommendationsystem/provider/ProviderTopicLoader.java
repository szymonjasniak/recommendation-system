package com.algoteque.recommendationsystem.provider;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.common.data.TopicName;
import com.algoteque.recommendationsystem.provider.data.ProviderConfiguration;
import com.algoteque.recommendationsystem.provider.data.ProviderTopics;
import com.algoteque.recommendationsystem.provider.data.Providers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class ProviderTopicLoader {

    public Providers loadProvidersConfig() throws IOException {
        String file = Files.readString(Paths.get("src/main/resources/static/provider_topic.json"));
        return loadProviders(file);
    }

    Providers loadProviders(String file) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ProviderConfiguration config = mapper.readValue(file, ProviderConfiguration.class);
        return new Providers(mapToProviderMap(config));
    }

    private Map<ProviderName, ProviderTopics> mapToProviderMap(ProviderConfiguration config) {
        Map<ProviderName, ProviderTopics> providersSetup = new HashMap<>();

        config.providerTopics()
                .forEach((key, value) -> {
                    List<TopicName> topics = Arrays.stream(value.split("\\+")).map(TopicName::new).toList();
                    providersSetup.put(
                            new ProviderName(key),
                            new ProviderTopics(topics)
                    );
                });
        return Collections.unmodifiableMap(providersSetup);
    }
}
