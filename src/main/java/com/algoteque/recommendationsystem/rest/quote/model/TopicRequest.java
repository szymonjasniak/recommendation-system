package com.algoteque.recommendationsystem.rest.quote.model;

import java.util.Map;

public record TopicRequest(Map<String, Integer> requestedTopics) {

}
