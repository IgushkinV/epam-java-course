package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.ProductRepository;
import com.epam.igushkin.homework.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @TestConfiguration
    static class ProductServiceTestConfiguration {

        @Bean
        ProductService productService(ProductRepository repository) {
            return new ProductServiceImpl(repository);
        }
    }

    @Test
    public void testFindByIdReturnsRightProduct() {
        var products = getProducts();
        var testProduct = products.get(0);
        var savedProduct = productService.save(testProduct);
        var finedProduct = productService.findById(savedProduct.getProductId());
        assertEquals(testProduct.getProductName(), finedProduct.getProductName());
    }

    @Test
    public void testFindByIdThrowsException() {
        assertThrows(NoEntityFoundException.class, () -> productService.findById(4));
    }

    @Test
    public void testUpdateChangesProductNumberAndDate() {
        var product = getProducts().get(0);
        var savedProduct = productService.save(product);
        var anotherProduct = new Product()
                .setProductId(savedProduct.getProductId())
                .setDiscontinued(savedProduct.isDiscontinued())
                .setSupplier(savedProduct.getSupplier())
                .setProductName("New Secret Product")
                .setUnitPrice(BigDecimal.valueOf(1500));
        var updatedProduct = productService.update(anotherProduct);
        assertNotEquals(product.getProductName(), updatedProduct.getProductName());
        assertNotEquals(product.getUnitPrice(), updatedProduct.getUnitPrice());
        assertEquals(anotherProduct.getProductId(), updatedProduct.getProductId());
        assertEquals(anotherProduct.getSupplier(), updatedProduct.getSupplier());
    }

    @Test
    public void testGetAllReturnsListOfTwoProducts() {
        var expected = 2;
        var products = getProducts();
        productService.save(products.get(0));
        productService.save(products.get(1));
        var returnedProducts = productService.getAll();
        var actual = returnedProducts.size();
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteReturnsTrue() {
        var products = getProducts();
        var savedProduct = productService.save(products.get(0));
        assertTrue(productService.delete(savedProduct.getProductId()));
    }

    @Test
    public void testDeleteReturnsFalse() {
        assertFalse(productService.delete(1));
    }


    /**
     * Создает два экзепляра Product для тестов.
     *
     * @return List<Product>
     */
    private List<Product> getProducts() {
        Supplier supplier = new Supplier()
                .setSupplierId(1)
                .setCompanyName("Best")
                .setPhone("+7-5555-4444");
        Supplier supplier2 = new Supplier()
                .setSupplierId(2)
                .setCompanyName("Best2")
                .setPhone("+7-5555-44442");
        Product product1 = new Product()
                .setProductId(1)
                .setProductName("Basic product")
                .setDiscontinued(false)
                .setUnitPrice(BigDecimal.valueOf(100))
                .setSupplier(supplier);
        Product product2 = new Product()
                .setProductId(2)
                .setProductName("Basic product 2")
                .setDiscontinued(false)
                .setUnitPrice(BigDecimal.valueOf(150))
                .setSupplier(supplier2);
        return List.of(product1, product2);
    }
}
