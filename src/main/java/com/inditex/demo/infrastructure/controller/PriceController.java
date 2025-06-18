package com.inditex.demo.infrastructure.controller;

import com.inditex.demo.application.PriceService;
import com.inditex.demo.domain.model.Price;
import com.inditex.demo.infrastructure.dto.PriceResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestController
public class PriceController implements PricesApi {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Override
    public ResponseEntity<PriceResponseDto> getPrice(
            OffsetDateTime date,
            Long productId,
            Long brandId
    ) {
        Price price = priceService.getApplicablePrice(date.toLocalDateTime(), productId, brandId);

        PriceResponseDto dto = new PriceResponseDto()
                .productId(price.productId())
                .brandId(price.brandId())
                .priceList(price.priceList())
                .startDate(price.startDate().atOffset(ZoneOffset.UTC))
                .endDate(price.endDate().atOffset(ZoneOffset.UTC))
                .price(price.price().doubleValue())
                .currency(price.currency());

        return ResponseEntity.ok(dto);
    }
}
