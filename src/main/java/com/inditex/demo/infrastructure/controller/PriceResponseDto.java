package com.inditex.demo.infrastructure.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponseDto(
        Long productId,
        Long brandId,
        Integer priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal price,
        String currency
) {}
