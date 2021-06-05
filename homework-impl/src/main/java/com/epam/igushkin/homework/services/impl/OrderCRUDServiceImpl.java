package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.repository.Repository;
import com.epam.igushkin.homework.repository.ipml.CustomerRepository;
import com.epam.igushkin.homework.repository.ipml.OrderRepository;
import com.epam.igushkin.homework.repository.ipml.ProductRepository;
import com.epam.igushkin.homework.services.CRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCRUDServiceImpl implements CRUDService<Order> {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public Optional<Order> create(JSONObject jsonObject) {
        var customerId = jsonObject.getInt("customer_id");
        var orderDate = LocalDateTime.parse(jsonObject.getString("order_date"));
        var orderNumber = jsonObject.optString("order_number");
        var totalAmount = jsonObject.getBigDecimal("total_amount");
        var productIdList = new ArrayList<Integer>();
        JSONArray jArray = jsonObject.getJSONArray("product_list"); //parsing the array of ids
        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++) {
                productIdList.add(jArray.getJSONObject(i).getInt("product_id"));
            }
        }
        var customerMadeOrder = customerRepository.read(customerId).get();
        Set<Product> productSet = new HashSet<>();
        for (int num : productIdList) {
            productSet.add(productRepository.read(num).get());
        }
        var order = new Order();
        var ordersOfCustomer = customerMadeOrder.getOrders();
        ordersOfCustomer.add(order);
        customerMadeOrder.setOrders(ordersOfCustomer);
        order.setOrderNumber(orderNumber);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(orderDate);
        order.setCustomer(customerMadeOrder);
        order.setProducts(productSet);
        return orderRepository.create(order);
    }

    @Override
    public Optional<Order> read(int id) {
        return orderRepository.read(id);
    }

    @Override
    public List<Order> readAll() {
        return orderRepository.readAll();
    }

    @Override
    public Optional<Order> update(int id, JSONObject jsonObject) {
        var orderNumber = jsonObject.getString("order_number");
        var customerIdd = jsonObject.getInt("customer_id");
        var totalAmount = jsonObject.getBigDecimal("total_amount");
        var updatingOrder = new Order();
        updatingOrder.setOrderNumber(orderNumber);
        updatingOrder.setCustomer(customerRepository.read(customerIdd).get());
        updatingOrder.setTotalAmount(totalAmount);
        return orderRepository.update(updatingOrder);
}

    @Override
    public boolean delete(int id) {
        return false;
    }

    public List<Order> getOrders(int customerId) {
        return customerRepository.read(customerId).get().getOrders();
    }
}
