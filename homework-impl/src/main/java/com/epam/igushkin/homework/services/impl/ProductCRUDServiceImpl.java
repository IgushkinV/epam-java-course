package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.repository.Repository;
import com.epam.igushkin.homework.repository.ipml.ProductRepository;
import com.epam.igushkin.homework.repository.ipml.SupplierRepository;
import com.epam.igushkin.homework.services.CRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCRUDServiceImpl implements CRUDService<Product> {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    @Override
    public Optional<Product> create(JSONObject jsonObject) {
        var name = jsonObject.getString("product_name");
        var unitPrice = jsonObject.getBigDecimal("unit_price");
        var supplierId = jsonObject.getInt("supplier_id");
        var isDiscontinued = jsonObject.getBoolean("is_discontinued");
        var newProduct = new Product();
        newProduct.setProductName(name);
        newProduct.setUnitPrice(unitPrice);
        newProduct.setDiscontinued(isDiscontinued);
        newProduct.setSupplier(supplierRepository.read(supplierId).get());
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
