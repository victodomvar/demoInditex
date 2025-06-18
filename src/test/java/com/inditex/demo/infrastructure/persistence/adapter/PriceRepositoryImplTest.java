package com.inditex.demo.infrastructure.persistence.adapter;

import com.inditex.demo.domain.model.Price;
import com.inditex.demo.infrastructure.persistence.entity.PriceEntity;
import com.inditex.demo.infrastructure.persistence.repository.PriceJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PriceRepositoryImplTest {

    private PriceJpaRepository priceJpaRepository;
    private PriceRepositoryImpl priceRepository;

    @BeforeEach
    void setUp() {
        priceJpaRepository = mock(PriceJpaRepository.class);
        priceRepository = new PriceRepositoryImpl(priceJpaRepository);
    }

    @Test
    void whenFindByProductIdAndBrandIdAndDate_thenReturnsMappedPrices() {
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);

        PriceEntity entity = new PriceEntity();
        entity.setBrandId(brandId);
        entity.setStartDate(date.minusHours(1));
        entity.setEndDate(date.plusHours(1));
        entity.setPriceList(1);
        entity.setProductId(productId);
        entity.setPriority(0);
        entity.setPrice(new BigDecimal("35.50"));
        entity.setCurrency("EUR");

        when(priceJpaRepository.findApplicablePrices(productId, brandId, date))
                .thenReturn(List.of(entity));

        List<Price> result = priceRepository.findByProductIdAndBrandIdAndDate(productId, brandId, date);

        assertEquals(1, result.size());
        Price price = result.get(0);
        assertEquals(brandId, price.brandId());
        assertEquals(productId, price.productId());
        assertEquals("EUR", price.currency());
        assertEquals(new BigDecimal("35.50"), price.price());

        verify(priceJpaRepository, times(1)).findApplicablePrices(productId, brandId, date);
    }
}
