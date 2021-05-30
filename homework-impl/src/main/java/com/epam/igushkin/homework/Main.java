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
import java.util.List;
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
        var customerName = "Additional";
        var customerPhone = "+777777777";
        customerUtils.create(customerName, customerPhone);
        customerUtils.update(1, "Updated-v3", "upd+33333");
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
        var orderNumber = "Add№";
        var customerId = 1;
        var orderDate = LocalDateTime.now();
        var totalAmount = BigDecimal.valueOf(1000);
        var order = orderUtils.create(orderNumber, customerId, orderDate, totalAmount, List.of(2));
        log.info("New Order was created and added: {}", order);
        orderUtils.update(1);
        orderUtils.delete(3);
    }

    public static void demonstrationSupplierUtils() {
        var supplierUtils = new SupplierUtils();
        var companyName = "EPAM";
        var phone = "+9999999990";
        var newSupplier = supplierUtils.create(companyName, phone);
        log.info("demonstrationSupplierUtils() - new supplier was created: {}", newSupplier);
        var tempSupplier = supplierUtils.create(companyName, phone);
        log.info("demonstrationSupplierUtils() - updating of tempSupplier: {}",
                supplierUtils.update(tempSupplier.getSupplierId()));
        log.info("demonstrationSupplierUtils() - deleting of tempSupplier: {}",
                supplierUtils.delete(tempSupplier.getSupplierId()));
    }

    public static void demonstrateProductUtils() {
        var productUtils = new ProductUtils();
        var productName = "IT secret system";
        var supplierId = 7;
        var unitPrice = BigDecimal.valueOf(150);
        productUtils.create(productName, supplierId, unitPrice, false);
        log.info("demonstrateProductUtils() - Read from table Product: {}", productUtils.read(3).get());
        productUtils.update(3, "Upd: Milk", 1, BigDecimal.valueOf(50), false);
        log.info("demonstrateProductUtils() - Delete product 3: {}", productUtils.delete(3));
    }
}
