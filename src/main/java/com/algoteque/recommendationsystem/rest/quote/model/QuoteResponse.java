package com.algoteque.recommendationsystem.rest.quote.model;

import java.math.BigDecimal;

public record QuoteResponse(String provider, BigDecimal quote) {
}
