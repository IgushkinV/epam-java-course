package com.epam.igushkin.homework.resources;

import com.epam.igushkin.homework.dto.ProductDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/product")
public interface ProductResource {

    @GetMapping("/{id}")
    ProductDTO get(@PathVariable Integer id);

    @GetMapping
    List<ProductDTO> getAllProducts();

    @PostMapping
    ProductDTO addProduct(@RequestBody ProductDTO productDTO);

    @PutMapping
    ProductDTO updateProduct(@RequestBody ProductDTO productDTO);

    @DeleteMapping("/{id}")
    boolean delete(@PathVariable("id") Integer id);
}
