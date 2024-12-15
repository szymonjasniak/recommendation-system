package com.algoteque.recommendationsystem.provider;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.common.data.TopicName;
import com.algoteque.recommendationsystem.provider.data.ProviderTopics;
import com.algoteque.recommendationsystem.provider.data.Providers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderTopicLoaderTest {

    ProviderTopicLoader loader = new ProviderTopicLoader();

    @Test
    void shouldLoadAndMapProvidersCorrectly() throws IOException {
        String file = Files.readString(Paths.get("src/test/resources/static/provider_topic_test.json"));

        Providers providers = loader.loadProviders(file);

        assertThat(providers).isNotNull();
        Map<ProviderName, ProviderTopics> providerSetup = providers.providersSetup();
        assertThat(providerSetup).hasSize(2);

        ProviderTopics providerATopics = providerSetup.get(new ProviderName("ProviderA"));
        assertThat(providerATopics).isNotNull();
        assertThat(providerATopics.getRank(new TopicName("Topic1"))).isEqualTo(0);
        assertThat(providerATopics.getRank(new TopicName("Topic2"))).isEqualTo(1);
        assertThat(providerATopics.getRank(new TopicName("Topic3"))).isEqualTo(2);
        assertThat(providerATopics.getRank(new TopicName("Topic4"))).isEqualTo(-1);
        assertThat(providerATopics.getRank(new TopicName("Topic5"))).isEqualTo(-1);


        ProviderTopics providerBTopics = providerSetup.get(new ProviderName("ProviderB"));
        assertThat(providerBTopics).isNotNull();
        assertThat(providerBTopics.getRank(new TopicName("Topic1"))).isEqualTo(-1);
        assertThat(providerBTopics.getRank(new TopicName("Topic2"))).isEqualTo(-1);
        assertThat(providerBTopics.getRank(new TopicName("Topic3"))).isEqualTo(-1);
        assertThat(providerBTopics.getRank(new TopicName("Topic4"))).isEqualTo(0);
        assertThat(providerBTopics.getRank(new TopicName("Topic5"))).isEqualTo(1);
    }
}
