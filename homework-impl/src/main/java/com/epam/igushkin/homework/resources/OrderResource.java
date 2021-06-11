package com.epam.igushkin.homework.resources;

import com.epam.igushkin.homework.dto.OrderDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/customer")
public interface OrderResource {

    @GetMapping("/{id}")
    OrderDTO get(@PathVariable Integer id);

    @GetMapping
    List<OrderDTO> getAllOrders();

    @PostMapping
    OrderDTO addOrder(@RequestBody OrderDTO orderDTO);

    @PutMapping
    OrderDTO updateOrder(@RequestBody OrderDTO orderDTO);

    @DeleteMapping("/{id}")
    boolean delete(@PathVariable("id") Integer id);
}
