package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.converters.supplier.DtoToSupplierConverter;
import com.epam.igushkin.homework.converters.supplier.SupplierToDTOConverter;
import com.epam.igushkin.homework.dto.SupplierDTO;
import com.epam.igushkin.homework.resources.SupplierResource;
import com.epam.igushkin.homework.services.impl.SupplierServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализует методы для получения, записи, удаления, передавая в DTO в сервисный слой.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class SupplierResourceImpl implements SupplierResource {

    private final SupplierServiceImpl supplierService;
    private final DtoToSupplierConverter dtoToSupplierConverter;
    private final SupplierToDTOConverter supplierToDTOConverter;

    /**
     * Получает поставщика по его id из базы.
     *
     * @param id уникальный номер поставщика в базе.
     * @return SupplierDTO, содержит данные полученного поставщика.
     */
    @Override
    public SupplierDTO get(Integer id) {
        var supplier = supplierService.findById(id);
        log.info("get() - {}", supplier);
        return supplierToDTOConverter.convert(supplier);
    }

    /**
     * Получает всех поставщиков из базы.
     *
     * @return List<SupplierDTO>
     */
    @Override
    public List<SupplierDTO> getAllSuppliers() {
        var suppliers = supplierService.getAll().stream()
                .map(supplierToDTOConverter::convert)
                .collect(Collectors.toList());
        log.info("getAllSuppliers() - {}", suppliers);
        return suppliers;
    }

    /**
     * Добавляет поставщика в базу.
     *
     * @param supplierDTO содержит данные поставщика, которого нужно добавить.
     * @return SupplierDTO, содержит данные поставщика, который был добавлен.
     */
    @Override
    public SupplierDTO addSupplier(SupplierDTO supplierDTO) {
        var supplier = dtoToSupplierConverter.convert(supplierDTO);
        var savedSupplierDTO = supplierToDTOConverter.convert(supplierService.save(supplier));
        log.info("addSupplier() - {}", savedSupplierDTO);
        return savedSupplierDTO;
    }

    /**
     * Обновляет данные поставщика в базе.
     *
     * @param supplierDTO содержит данные, которые нужно обновить.
     * @return SupplierDTO содержит обновленные данные.
     */
    @Override
    public SupplierDTO updateSupplier(SupplierDTO supplierDTO) {
        var supplier = dtoToSupplierConverter.convert(supplierDTO);
        var updatedSupplier = supplierService.update(supplier);
        log.info("updateSupplier() - {}", updatedSupplier);
        return supplierToDTOConverter.convert(updatedSupplier);
    }

    /**
     * Удаляет поставщика из базы.
     *
     * @param id уникальный номер поставщика в базе.
     * @return результат удаления (true или false).
     */
    @Override
    public boolean delete(Integer id) {
        boolean result = supplierService.delete(id);
        log.info("delete() - {}", result);
        return result;
    }
}
