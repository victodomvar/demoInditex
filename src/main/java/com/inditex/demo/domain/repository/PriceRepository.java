package com.inditex.demo.domain.repository;

import com.inditex.demo.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    List<Price> findByProductIdAndBrandIdAndDate(Long productId, Long brandId, LocalDateTime date);
}
