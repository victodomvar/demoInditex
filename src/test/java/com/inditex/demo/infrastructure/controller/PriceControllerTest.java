package com.inditex.demo.infrastructure.controller;

import com.inditex.demo.application.PriceService;
import com.inditex.demo.domain.model.Price;
import com.inditex.demo.infrastructure.dto.PriceResponseDto;
import com.inditex.demo.infrastructure.exception.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    @Test
    void whenValidRequest_thenReturnsPrice() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price mockPrice = new Price(1L, date.minusHours(1), date.plusHours(1), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");

        when(priceService.getApplicablePrice(any(), any(), any())).thenReturn(mockPrice);

        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T10:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void whenNoApplicablePrice_thenReturns404() throws Exception {
        when(priceService.getApplicablePrice(any(), any(), any()))
                .thenThrow(new PriceNotFoundException("No applicable price found"));

        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T21:00:00Z")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenMissingParameters_thenReturns400() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T10:00:00Z")
                        .param("brandId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
