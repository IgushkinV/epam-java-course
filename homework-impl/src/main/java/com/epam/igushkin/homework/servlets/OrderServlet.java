package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.repository.IRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServlet extends HttpServlet {

    private final IRepository<Order> orderRepository;
    private final IRepository<Customer> customerRepository;
    private final IRepository<Product> productRepository;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        var map = request.getParameterMap();
        if (map.containsKey("customerId")) {
            var paramCustomer = Integer.parseInt(request.getParameter("customerId"));
            response.getWriter().println(customerRepository.read(paramCustomer).get().getOrders());
        }
        if (map.containsKey("id")) {
            var paramId = Integer.parseInt(request.getParameter("id"));
            response.getWriter().println(orderRepository.read(paramId).get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        var jsonObject = requestToJSON(request);
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
        var addedOrder = orderRepository.create(order);
        log.info("doPost() - Заказ записывается в БД: {} ", order);
        log.debug("doPost() - Заказ записан в БД: {} ", addedOrder);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        requestToJSON(request);
        var success = false;
        JSONObject jsonObject = null;
        try {
            jsonObject = requestToJSON(request);
        } catch (IOException e) {
            log.error("doPut() - Ошибка при парсинге JSON запроса.", e);
        }
        var id = Integer.parseInt(request.getParameter("id"));
        var orderNumber = jsonObject.getString("order_number");
        var customerIdd = jsonObject.getInt("customer_id");
        var totalAmount = jsonObject.getBigDecimal("total_amount");
        var updatingOrderOptional = orderRepository.read(id);
        if (updatingOrderOptional.isPresent()) {
            var updatingOrder = updatingOrderOptional.get();
            updatingOrder.setOrderNumber(orderNumber);
            updatingOrder.setCustomer(customerRepository.read(customerIdd).get());
            updatingOrder.setTotalAmount(totalAmount);
            orderRepository.update(updatingOrder);
            success = true;
        }
        if (success) {
            response.getWriter().println("The update was successful.");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter().println("The update was unsuccessful.");
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        if (orderRepository.read(id).isEmpty()) {
            response.getWriter().println("Попытка удалить несуществующую запись.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        orderRepository.delete(id);
        response.getWriter().println("Order № " + id + " удалён.");
    }

    private void setContentTypeAndEncoding(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private JSONObject requestToJSON(HttpServletRequest request) throws IOException {
        var inputStream = request.getInputStream();
        var strRequest = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        if (strRequest.equals("")) {
            return null;
        }
        return new JSONObject(strRequest);
    }
}
