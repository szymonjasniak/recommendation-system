package com.algoteque.recommendationsystem.quote.data;

import com.algoteque.recommendationsystem.common.data.ProviderName;

import java.math.BigDecimal;

public record Quote(ProviderName provider, BigDecimal quote) {
}
