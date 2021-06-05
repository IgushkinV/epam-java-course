package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.repository.ipml.SupplierRepository;
import com.epam.igushkin.homework.services.CRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierCRUDServiceImpl implements CRUDService<Supplier> {

    private final SupplierRepository supplierRepository;

    @Override
    public Optional<Supplier> create(JSONObject jsonObject) {
        var supplierName = jsonObject.optString("company_name");
        var phone = jsonObject.optString("phone");
        var tempSupplier = new Supplier();
        tempSupplier.setCompanyName(supplierName);
        tempSupplier.setPhone(phone);
        return supplierRepository.create(tempSupplier);
    }

    @Override
    public Optional<Supplier> read(int id) {
        return supplierRepository.read(id);
    }

    @Override
    public List<Supplier> readAll() {
        return supplierRepository.readAll();
    }

    @Override
    public Optional<Supplier> update(int id, JSONObject jsonObject) {
        var companyName = jsonObject.getString("company_name");
        var phone = jsonObject.getString("phone");
        var newSupplier = new Supplier();
        newSupplier.setCompanyName(companyName);
        newSupplier.setPhone(phone);
        newSupplier.setSupplierId(id);
        return supplierRepository.update(newSupplier);
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
