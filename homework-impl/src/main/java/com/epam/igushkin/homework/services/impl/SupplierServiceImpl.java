package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.SupplierRepository;
import com.epam.igushkin.homework.services.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);

    }

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findById(Long id) {
        var supplierOptional = supplierRepository.findById(id);
        return supplierOptional.orElseThrow(() -> new NoEntityFoundException("Невозможно найти Поставщика с номером " + id));
    }

    @Override
    public Supplier update(Long id, Supplier supplier) {
        var oldSupplierOpt = supplierRepository.findById(id);
        if (oldSupplierOpt.isEmpty()) {
            throw new NoEntityFoundException("Поставщик с id" + id + "не найден.");
        }
        var oldSupplier = oldSupplierOpt.get();
        oldSupplier.setCompanyName(supplier.getCompanyName())
                .setPhone(supplier.getPhone());
        return supplierRepository.save(oldSupplier);
    }

    @Override
    public boolean delete(Long id) {
        boolean success = false;
        if (supplierRepository.existsById(id)) {
            supplierRepository.delete(supplierRepository.findById(id).get());
            success = true;
            log.info("delete() - Удаление прошло успешно.");
        } else {
            log.info("delete() - Нечего удалить.");
        }
        return success;
    }
}
