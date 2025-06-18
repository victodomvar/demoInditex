package com.inditex.demo.infrastructure.persistence.adapter;

import com.inditex.demo.domain.model.Price;
import com.inditex.demo.domain.repository.PriceRepository;
import com.inditex.demo.infrastructure.persistence.entity.PriceEntity;
import com.inditex.demo.infrastructure.persistence.repository.PriceJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository jpaRepository;

    public PriceRepositoryImpl(PriceJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Price> findByProductIdAndBrandIdAndDate(Long productId, Long brandId, LocalDateTime date) {
        return jpaRepository.findApplicablePrices(productId, brandId, date).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}
