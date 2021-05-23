package com.epam.igushkin.homework;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.utils.CustomerUtils;
import com.epam.igushkin.homework.utils.OrderUtils;
import com.epam.igushkin.homework.utils.ProductUtils;
import com.epam.igushkin.homework.utils.SupplierUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class Main {
    public static void main(String[] args) {
        demonstrationCustomerUtils();
        demonstrationOrderUtils();
        demonstrationSupplierUtils();
        demonstrateProductUtils();
    }
    public static void demonstrationCustomerUtils() {
        CustomerUtils customerUtils = new CustomerUtils();
        log.info("demonstrationCustomerUtils() - 5th line customer: {}", customerUtils.read(5).get());
        Customer customerToAdd = new Customer();
        customerToAdd.setCustomerName("Additional");
        customerToAdd.setPhone("+777777777");
        customerUtils.create(customerToAdd);
        customerUtils.update(1);
        customerUtils.delete(3);
    }

    public static void demonstrationOrderUtils() {
        OrderUtils orderUtils = new OrderUtils();
        Optional<Order> opt = orderUtils.read(5);
        if (opt.isPresent()) {
            log.info("demonstrationOrderUtils() - order with id = 5: {}", opt.get());
        } else {
            log.info("demonstrationOrderUtils() - No order with id = 5");
        }
        Order order = new Order();
        order.setOrderNumber("Add№");
        order.setOrderDate(LocalDateTime.now());
        CustomerUtils tempCustomerUtil = new CustomerUtils();
        Customer tempCustomer = tempCustomerUtil.read(1).get();
        tempCustomerUtil.entityManagerClose();
        order.setCustomer(tempCustomer);
        order.setTotalAmount(BigDecimal.valueOf(1000));
        orderUtils.create(order);
        orderUtils.update(1);
        orderUtils.delete(3);
    }

    public static void demonstrationSupplierUtils() {
        SupplierUtils supplierUtils = new SupplierUtils();
        Supplier supplier = new Supplier();
        supplier.setCompanyName("EPAM");
        supplierUtils.create(supplier);
        supplierUtils.update(10);
        supplierUtils.delete(1);
        supplierUtils.entityManagerClose();
    }

    public static void demonstrateProductUtils() {
        ProductUtils productUtils = new ProductUtils();
        Product product = new Product();
        product.setProductName("IT secret system");
        SupplierUtils tempUtils = new SupplierUtils();
        Supplier tempSupplier = tempUtils.read(7).get();
        product.setSupplier(tempSupplier);
        product.setDiscontinued(true);
        productUtils.create(product);
    }
}
