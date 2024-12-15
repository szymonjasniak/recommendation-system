package com.algoteque.recommendationsystem.rest.quote;

import com.algoteque.recommendationsystem.common.data.ProviderName;
import com.algoteque.recommendationsystem.quote.QuoteService;
import com.algoteque.recommendationsystem.quote.data.Quote;
import com.algoteque.recommendationsystem.rest.quote.model.TopicRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class QuotesControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private MockMvc mockMvc;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private QuoteMapper quoteMapper;
    @Mock
    private QuoteService quoteService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new QuotesController(quoteMapper, quoteService)).build();
    }

    @Test
    public void calculateQuotes_shouldReturnNotEmptyResponse() throws Exception {
        TopicRequest topicRequest = new TopicRequest(Map.of(
                "history", 15,
                "art", 10
        ));
        when(quoteService.calculateQuotes(any())).thenReturn(Set.of(new Quote(new ProviderName("provider a"), BigDecimal.TEN)));

        mockMvc.perform(
                        post("/rest/quote/_calculate")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsBytes(topicRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void calculateQuotes_shouldReturnEmptyResponse() throws Exception {
        TopicRequest topicRequest = new TopicRequest(Map.of(
                "history", 15
        ));
        when(quoteService.calculateQuotes(any())).thenReturn(Set.of());

        mockMvc.perform(
                        post("/rest/quote/_calculate")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsBytes(topicRequest)))
                .andExpect(status().isNoContent());
    }
}