package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.SupplierRepository;
import com.epam.igushkin.homework.services.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SupplierServiceImplTest {

    @Autowired
    private SupplierService supplierService;

    @TestConfiguration
    static class SupplierServiceTestConfiguration {
        @Bean
        SupplierServiceImpl supplierService(SupplierRepository repository) {
            return new SupplierServiceImpl(repository);
        }
    }

    @Test
    public void testFindByIdReturnsRightSupplier() {
        var suppliers = getSuppliers();
        var testSupplier = suppliers.get(0);
        var savedSupplier = supplierService.save(testSupplier);
        var findSupplier = supplierService.findById(savedSupplier.getSupplierId());
        assertEquals(testSupplier.getCompanyName(), findSupplier.getCompanyName());
    }

    @Test
    public void testFindByIdThrowsException() {
        assertThrows(NoEntityFoundException.class, () -> supplierService.findById(0));
    }

    @Test
    public void testDeleteReturnsTrue() {
        var suppliers = getSuppliers();
        var savedSupplier = supplierService.save(suppliers.get(0));
        assertTrue(supplierService.delete(savedSupplier.getSupplierId()));
    }

    @Test
    public void testDeleteReturnsFalse() {
        assertFalse(supplierService.delete(0));
    }

    @Test
    public void testGetAllReturnsListOfTwoSuppliers() {
        var expected = 2;
        var suppliers = getSuppliers();
        supplierService.save(suppliers.get(0));
        supplierService.save(suppliers.get(1));
        var returnedSuppliers = supplierService.getAll();
        var actual = returnedSuppliers.size();
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateChangesSupplierName() {
        var suppliers = getSuppliers();
        var savedSupplier = supplierService.save(suppliers.get(0));
        savedSupplier.setSupplierId(savedSupplier.getSupplierId())
                .setCompanyName("New Company")
                .setPhone(savedSupplier.getPhone());
        var updatedSupplier = supplierService.update(savedSupplier);
        assertNotEquals(getSuppliers().get(0).getCompanyName(), updatedSupplier.getCompanyName());
        assertEquals(savedSupplier.getCompanyName(), updatedSupplier.getCompanyName());
    }

    /**
     * Создает два экзепляра Supplier для тестов.
     *
     * @return List<Supplier>
     */
    private List<Supplier> getSuppliers() {
        Supplier supplier = new Supplier()
                .setCompanyName("The Best Company")
                .setPhone("+7-1111-11111");
        Supplier supplier2 = new Supplier()
                .setCompanyName("Yhe Best Company 2")
                .setPhone("+7-2222-22222");
        return List.of(supplier, supplier2);
    }

}
