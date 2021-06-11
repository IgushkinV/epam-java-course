package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.dto.SupplierDTO;
import com.epam.igushkin.homework.exceptions.MyServiceException;
import com.epam.igushkin.homework.repository.ipml.SupplierRepository;
import com.epam.igushkin.homework.services.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements Service<Supplier, SupplierDTO> {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierDTO create(SupplierDTO dto) {
        SupplierDTO createdSupplierDTO = null;
        var supplier = mapDTOToEntity(dto);
        var createdSupplierOpt = supplierRepository.create(supplier);
        if (createdSupplierOpt.isPresent()) {
            return mapEntityToDTO(createdSupplierOpt.get());
        } else {
            throw new MyServiceException("Получение null при попытке считать записанный ранее объект.");
        }
    }

    @Override
    public SupplierDTO read(int id) {
        var readSupplierOpt = supplierRepository.read(id);
        if (readSupplierOpt.isPresent()) {
            return mapEntityToDTO(readSupplierOpt.get());
        } else {
            throw new MyServiceException("Получение null при попытке считать из репозитория.");
        }
    }

    @Override
    public List<SupplierDTO> readAll() {
        return supplierRepository.readAll().stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO update(SupplierDTO dto) {
        var updatedSupplier = supplierRepository.update(mapDTOToEntity(dto));
        if (updatedSupplier.isPresent()) {
            return mapEntityToDTO(updatedSupplier.get());
        } else {
            throw new MyServiceException("Получение null при чтении обновленного объекта из репозитория.");
        }
    }

    @Override
    public boolean delete(int id) {
        return supplierRepository.delete(id);
    }

    @Override
    public Supplier mapDTOToEntity(SupplierDTO dto) {
        var supplier = new Supplier();
        supplier.setCompanyName(dto.getCompanyName());
        if (Objects.nonNull(dto.getPhone())) {
            supplier.setPhone(dto.getPhone());
        }
        return supplier;
    }

    @Override
    public SupplierDTO mapEntityToDTO(Supplier entity) {
        return SupplierDTO.builder()
                .supplierId(entity.getSupplierId())
                .companyName(entity.getCompanyName())
                .phone(entity.getPhone())
                .build();
    }
}
