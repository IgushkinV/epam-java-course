package com.epam.igushkin.homework.resources.impl;
import com.epam.igushkin.homework.converters.product.DtoToProductConverter;
import com.epam.igushkin.homework.converters.product.ProductToDTOConverter;
import com.epam.igushkin.homework.dto.ProductDTO;
import com.epam.igushkin.homework.resources.ProductResource;
import com.epam.igushkin.homework.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j@RequiredArgsConstructor
public class ProductResourceImpl implements ProductResource {

    private final ProductServiceImpl productService;
    private final DtoToProductConverter dtoToProductConverter;
    private final ProductToDTOConverter productToDTOConverter;

    /**
     * Получает Product по id из базы.
     *
     * @param id уникальный номер продукта в базе.
     * @return ProductDTO, содержащий данные запрошенного продукта.
     */
    @Override
    public ProductDTO get(Integer id) {
        var product = productService.findById(id);
        log.info("get() - {}", product);
        return productToDTOConverter.convert(product);
    }

    /**
     * Получает все продукты из базы.
     *
     * @return List<ProductDTO>
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        var products = productService.getAll().stream()
                .map(productToDTOConverter::convert)
                .collect(Collectors.toList());
        log.info("getAllProducts() - {}", products);
        return products;
    }

    /**
     * Добавляет продукт в базу.
     *
     * @param productDTO содержит данные продукта, который нужно добавить.
     * @return ProductDTO с данными добавленного продукта.
     */
    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        var product = dtoToProductConverter.convert(productDTO);
        var savedProductDTO = productToDTOConverter.convert(productService.save(product));
        log.info("addProduct() - {}", savedProductDTO);
        return savedProductDTO;
    }

    /**
     * Обновляет данные продукта по id.
     *
     * @param productDTO содержит данные для обновления продукта.
     * @return ProductDTO, содержит данные обновленного продукта.
     */
    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        var product = productService.findById(productDTO.getProductId());
        product.setProductName(productDTO.getProductName())
                .setUnitPrice(productDTO.getUnitPrice())
                .setDiscontinued(productDTO.isDiscontinued());
        var updatedProduct = productService.save(product);
        log.info("updateProduct() - {}", updatedProduct);
        return productToDTOConverter.convert(updatedProduct);
    }

    /**
     * Удаляет продукт из базы по его id.
     *
     * @param id уникальный номер продукта в базе.
     * @return результат удаления (true или false).
     */
    @Override
    public boolean delete(Integer id) {
        boolean result = productService.delete(id);
        log.info("delete() - {}", result);
        return result;
    }
}
