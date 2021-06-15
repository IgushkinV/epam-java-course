package com.epam.igushkin.homework.converters.supplier;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.dto.ProductDTO;
import com.epam.igushkin.homework.dto.SupplierDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DtoToSupplierConverter implements Converter<SupplierDTO, Supplier> {
    @Override
    public Supplier convert(SupplierDTO dto) {
        var supplier = new Supplier()
                .setCompanyName(dto.getCompanyName())
                .setPhone(dto.getPhone());
        log.info("convert() - DTO {} to Supplier {}", dto, supplier);
        return supplier;
    }
}
