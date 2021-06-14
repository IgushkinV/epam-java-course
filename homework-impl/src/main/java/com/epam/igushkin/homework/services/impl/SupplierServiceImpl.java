package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.SupplierRepository;
import com.epam.igushkin.homework.services.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final MessageSource errorSource;

    /**
     * Сохраняет поставщика в репозитории.
     *
     * @param supplier данные поставщика для сохранения.
     * @return сохраненный поставщик.
     */
    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);

    }

    /**
     * Возвращает всех поставщиков из репозитория.
     *
     * @return список поставщиков.
     */
    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    /**
     * Находит поставщика по его id в репозитории.
     *
     * @param id уникальный номер поставщика.
     * @return найденный поставщик.
     */
    @Override
    public Supplier findById(Integer id) {
        var supplierOptional = supplierRepository.findById(id);
        Object[] args = {id.intValue()};
        String exceptionMessageLocale = errorSource.getMessage("noSupplier", args, Locale.getDefault());
        return supplierOptional.orElseThrow(() -> new NoEntityFoundException(exceptionMessageLocale));
    }

    /**
     * Обвновляет данные поставщика в репозитории.
     *
     * @param id уникальный номер поставщика.
     * @param supplier данные для обновления поставщика.
     * @return обновленный поставщик.
     */
    @Override
    public Supplier update(Integer id, Supplier supplier) {
        var oldSupplierOpt = supplierRepository.findById(id);
        if (oldSupplierOpt.isEmpty()) {
            throw new NoEntityFoundException("Поставщик с id" + id + "не найден.");
        }
        var oldSupplier = oldSupplierOpt.get();
        oldSupplier.setCompanyName(supplier.getCompanyName())
                .setPhone(supplier.getPhone());
        return supplierRepository.save(oldSupplier);
    }

    /**
     * Удаляет поставщика из репозитория.
     *
     * @param id уникальный номер поставщика.
     * @return результат удаления поставщика (true или false).
     */
    @Override
    public boolean delete(Integer id) {
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
