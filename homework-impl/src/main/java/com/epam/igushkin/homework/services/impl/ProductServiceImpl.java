package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.dto.ProductDTO;
import com.epam.igushkin.homework.repository.ipml.ProductRepository;
import com.epam.igushkin.homework.repository.ipml.SupplierRepository;
import com.epam.igushkin.homework.services.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ProductServiceImpl implements Service<Product, ProductDTO> {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductDTO create(ProductDTO dto) {
        var newProduct = new Product();
        newProduct.setProductName(dto.getProductName());
        newProduct.setUnitPrice(dto.getUnitPrice());
        newProduct.setDiscontinued(dto.isDiscontinued());
        newProduct.setSupplier(supplierRepository.read(dto).get());
        var product = productRepository.create(newProduct);
        return product;
    }

    @Override
    public Optional<Product> read(int id) {
        return productRepository.read(id);
    }

    @Override
    public List<Product> readAll() {
        return productRepository.readAll();
    }

    @Override
    public Optional<Product> update(int id, JSONObject jsonObject) {
        var productName = jsonObject.getString("product_name");
        var supplierId = jsonObject.getInt("supplier_id");
        var unitPrice = jsonObject.getBigDecimal("unit_price");
        var isDiscontinued = jsonObject.getBoolean("is_discontinued");
        var updatedProduct = new Product();
        updatedProduct.setProductId(id);
        updatedProduct.setProductName(productName);
        updatedProduct.setSupplier(supplierRepository.read(supplierId).get());
        updatedProduct.setUnitPrice(unitPrice);
        updatedProduct.setDiscontinued(isDiscontinued);
        return productRepository.update(updatedProduct);
    }

    @Override
    public boolean delete(int id) {
        return productRepository.delete(id);
    }
}
