package com.algoteque.recommendationsystem.rest.quote;

import com.algoteque.recommendationsystem.quote.QuoteService;
import com.algoteque.recommendationsystem.quote.data.Quote;
import com.algoteque.recommendationsystem.rest.quote.model.QuoteResponse;
import com.algoteque.recommendationsystem.rest.quote.model.TopicRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("rest/quote")
public class QuotesController {

    private final QuoteMapper quoteMapper;
    private final QuoteService quoteService;

    public QuotesController(QuoteMapper quoteMapper, QuoteService quoteService) {
        this.quoteMapper = quoteMapper;
        this.quoteService = quoteService;
    }

    @PostMapping(value = "_calculate")
    public ResponseEntity<Set<QuoteResponse>> calculateQuotes(@RequestBody TopicRequest topicRequest) {
        Set<Quote> quotes = quoteService.calculateQuotes(quoteMapper.map(topicRequest));
        if (quotes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(quoteMapper.map(quotes));
        }
    }


}
