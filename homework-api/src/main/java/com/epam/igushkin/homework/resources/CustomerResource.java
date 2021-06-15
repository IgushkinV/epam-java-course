package com.epam.igushkin.homework.resources;

import com.epam.igushkin.homework.dto.CustomerDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/customer")
public interface CustomerResource {

    @GetMapping("{id}")
    CustomerDTO getCustomer(@PathVariable("id") Integer id);

    @GetMapping
    List<CustomerDTO> getAllCustomers();

    @PostMapping
    CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO);

    @PutMapping("/{id}")
    CustomerDTO updateCustomer(@PathVariable Integer id,
                               @RequestBody CustomerDTO customerDTO);

    @DeleteMapping("/{id}")
    boolean deleteCustomer(@PathVariable Integer id);

}
