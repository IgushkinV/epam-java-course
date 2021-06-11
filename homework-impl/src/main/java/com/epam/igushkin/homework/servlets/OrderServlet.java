package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.dto.OrderDTO;
import com.epam.igushkin.homework.services.impl.CustomerServiceImpl;
import com.epam.igushkin.homework.services.impl.OrderServiceImpl;
import com.epam.igushkin.homework.services.impl.ProductServiceImpl;
import com.epam.igushkin.homework.servlets.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@RequiredArgsConstructor
public class OrderServlet extends HttpServlet {

    private final OrderServiceImpl orderService;
    private final CustomerServiceImpl customerService;
    private final ProductServiceImpl productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var map = request.getParameterMap();
        if (map.containsKey("customerId")) {
            var paramCustomer = Integer.parseInt(request.getParameter("customerId"));
            response.getWriter().println(orderService.getOrders(paramCustomer));
        }
        if (map.containsKey("id")) {
            var paramId = Integer.parseInt(request.getParameter("id"));
            response.getWriter().println(orderService.read(paramId).get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        /*----------------*/
        var customerMadeOrder = customerService.read(jsonObject.getInt("customer_id"));
        /*------------------------------ вынести отсюда -----------*/
        var productIdList = new ArrayList<Integer>();
        JSONArray jArray = jsonObject.getJSONArray("product_list"); //parsing the array of ids
        if (jArray != null) {
            for (int i = 0; i < jArray.length(); i++) {
                productIdList.add(jArray.getJSONObject(i).getInt("product_id"));
            }
        }
        Set<Product> productSet = new HashSet<>();
        for (int num : productIdList) {
            productSet.add(productService.read(num).get());
        }
        /*----------------------------------------------------------*/
        var orderDate = LocalDateTime.parse(jsonObject.getString("order_date"));
        var orderDTO = OrderDTO.builder()
                .orderNumber(jsonObject.optString("order_number"))
                .customer(customerService.read(jsonObject.getInt("customer_id")))
                .orderDate(orderDate)
                .totalAmount(jsonObject.getBigDecimal("total_amount"))
                .products()

        var order = new Order();
        var ordersOfCustomer = customerMadeOrder.getOrders();
        ordersOfCustomer.add(order);
        customerMadeOrder.setOrders(ordersOfCustomer);
        order.setOrderNumber(orderNumber);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(orderDate);
        order.setCustomer(customerMadeOrder);
        order.setProducts(productSet);

        /*------------------------  было  */
        var addedOrder = orderService.create(jsonObject);
        log.info("doPost() - Заказ передан для записи в БД. Сервис вернул: {} ", addedOrder);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        JSONObject jsonObject = null;
        try {
            jsonObject = ServletUtils.requestToJSON(request);
        } catch (IOException e) {
            log.error("doPut() - Ошибка при парсинге JSON запроса.", e);
        }
        var id = Integer.parseInt(request.getParameter("id"));
        var updatedOrder = orderService.update(id, jsonObject);
        if (updatedOrder.isPresent()) {
            response.getWriter().println(updatedOrder.get());
            log.info("doPut() - Заказ № {} обновлен.", id);
        } else {
            log.info("doPut() - Не удалось обновить заказ № {}", id);
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        var result = orderService.delete(id);
        if (result) {
            response.getWriter().println("Order № " + id + " удалён.");
        } else {
            response.getWriter().println("Не удалось удалить Order № " + id);
        }
    }
}
