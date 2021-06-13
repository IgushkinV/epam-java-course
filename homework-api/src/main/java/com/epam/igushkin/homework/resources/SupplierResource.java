package com.epam.igushkin.homework.resources;

import com.epam.igushkin.homework.dto.SupplierDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/supplier")
public interface SupplierResource {

    @GetMapping("/{id}")
    SupplierDTO get(@PathVariable Integer id);

    @GetMapping
    List<SupplierDTO> getAllSuppliers();

    @PostMapping
    SupplierDTO addSupplier(@RequestBody SupplierDTO supplierDTO);

    @PutMapping
    SupplierDTO updateSupplier(@RequestBody SupplierDTO supplierDTO);

    @DeleteMapping("/{id}")
    boolean delete(@PathVariable("id") Integer id);
}
