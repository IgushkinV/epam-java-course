package com.epam.igushkin.homework.converters.product;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.dto.ProductDTO;
import com.epam.igushkin.homework.services.impl.SupplierServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DtoToProductConverter implements Converter<ProductDTO, Product> {

    private final SupplierServiceImpl supplierService;

    @Override
    public Product convert(ProductDTO dto) {
        var product = new Product()
                .setProductName(dto.getProductName())
                .setDiscontinued(dto.isDiscontinued())
                .setUnitPrice(dto.getUnitPrice())
                .setSupplier(supplierService.findById(dto.getSupplierId()));
        log.info("convert() - DTO {} to Product {}", dto, product);
        return product;
    }
}
