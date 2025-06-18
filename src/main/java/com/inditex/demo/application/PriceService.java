package com.inditex.demo.application;

import com.inditex.demo.domain.model.Price;
import com.inditex.demo.domain.repository.PriceRepository;
import com.inditex.demo.infrastructure.exception.PriceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class PriceService {

    private final PriceRepository repository;

    public PriceService(PriceRepository repository) {
        this.repository = repository;
    }

    public Price getApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
        return repository.findByProductIdAndBrandIdAndDate(productId, brandId, date).stream()
                .max(Comparator.comparingInt(Price::priority)) //    el de  mayor priioridad
                .orElseThrow(() -> new PriceNotFoundException("No applicable price found"));
    }


}
