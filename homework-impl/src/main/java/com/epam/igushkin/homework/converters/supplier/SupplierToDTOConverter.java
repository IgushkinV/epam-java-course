package com.epam.igushkin.homework.converters.supplier;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.dto.ProductDTO;
import com.epam.igushkin.homework.dto.SupplierDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SupplierToDTOConverter implements Converter<Supplier, SupplierDTO> {
    @Override
    public SupplierDTO convert(Supplier supplier) {
        var dto = SupplierDTO.builder()
                .companyName(supplier.getCompanyName())
                .phone(supplier.getPhone())
                .build();
        log.info("convert() - Supplier {} to DTO {}", supplier, dto);
        return dto;
    }
}
