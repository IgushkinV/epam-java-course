package com.epam.igushkin.homework.converters.product;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.dto.OrderDTO;
import com.epam.igushkin.homework.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductToDTOConverter implements Converter<Product, ProductDTO> {
    @Override
    public ProductDTO convert(Product product) {
        var dto = ProductDTO.builder()
                .isDiscontinued(product.isDiscontinued())
                .productName(product.getProductName())
                .unitPrice(product.getUnitPrice())
                .build();
        log.info("convert() - Product {} to DTO {}", product, dto);
        return dto;
    }
}
