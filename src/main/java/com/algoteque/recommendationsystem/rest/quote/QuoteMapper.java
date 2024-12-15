package com.algoteque.recommendationsystem.rest.quote;

import com.algoteque.recommendationsystem.common.data.TopicName;
import com.algoteque.recommendationsystem.quote.data.Quote;
import com.algoteque.recommendationsystem.quote.data.QuoteCalculationRequest;
import com.algoteque.recommendationsystem.rest.quote.model.QuoteResponse;
import com.algoteque.recommendationsystem.rest.quote.model.TopicRequest;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class QuoteMapper {

    public Set<QuoteResponse> map(Set<Quote> quotes) {
        return quotes.stream()
                .map(quote -> new QuoteResponse(quote.provider().name(), quote.quote()))
                .collect(Collectors.toSet());
    }

    public QuoteCalculationRequest map(TopicRequest topicRequest) {
        return new QuoteCalculationRequest(topicRequest.requestedTopics()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> new TopicName(entry.getKey()), Map.Entry::getValue)));
    }
}
