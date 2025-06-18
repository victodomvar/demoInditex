package com.inditex.demo.application;

import com.inditex.demo.domain.model.Price;
import com.inditex.demo.domain.repository.PriceRepository;
import com.inditex.demo.infrastructure.exception.PriceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenOneApplicablePrice_thenReturnIt() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price expectedPrice = new Price(
                brandId,
                date.minusHours(1),
                date.plusHours(2),
                1,
                productId,
                0,
                new BigDecimal("35.50"),
                "EUR");

        when(priceRepository.findByProductIdAndBrandIdAndDate(productId, brandId, date))
                .thenReturn(List.of(expectedPrice));

        Price result = priceService.getApplicablePrice(date, productId, brandId);

        assertEquals(expectedPrice, result);
    }

    @Test
    void whenMultipleApplicablePrices_thenReturnHighestPriority() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price lowPriority = new Price(
                brandId,
                date.minusHours(5),
                date.plusHours(5),
                1,
                productId,
                0,
                new BigDecimal("35.50"),
                "EUR");

        Price highPriority = new Price(
                brandId,
                date.minusHours(1),
                date.plusHours(2),
                2,
                productId,
                1,
                new BigDecimal("25.45"),
                "EUR");

        when(priceRepository.findByProductIdAndBrandIdAndDate(productId, brandId, date))
                .thenReturn(List.of(lowPriority, highPriority));

        Price result = priceService.getApplicablePrice(date, productId, brandId);

        assertEquals(highPriority, result);
    }

    @Test
    void whenNoApplicablePrice_thenThrowException() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 13, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        when(priceRepository.findByProductIdAndBrandIdAndDate(productId, brandId, date))
                .thenReturn(List.of());

        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class,
                () -> priceService.getApplicablePrice(date, productId, brandId));

        assertEquals("No applicable price found", exception.getMessage());
    }

    @Test
    void whenTwoPricesWithSamePriority_thenReturnFirstOne() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price first = new Price(
                brandId,
                date.minusHours(1),
                date.plusHours(2),
                3,
                productId,
                1,
                new BigDecimal("30.50"),
                "EUR");

        Price second = new Price(
                brandId,
                date.minusHours(2),
                date.plusHours(3),
                4,
                productId,
                1,
                new BigDecimal("38.95"),
                "EUR");

        when(priceRepository.findByProductIdAndBrandIdAndDate(productId, brandId, date))
                .thenReturn(List.of(first, second));

        Price result = priceService.getApplicablePrice(date, productId, brandId);

        assertEquals(first, result);
    }
}
