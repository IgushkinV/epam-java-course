package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.utils.CustomerUtils;
import com.epam.igushkin.homework.utils.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Slf4j
public class OrderServlet extends HttpServlet {

    private final OrderUtils orderUtils = new OrderUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        var map = request.getParameterMap();
        if (map.containsKey("customerId")) {
            var paramCustomer = Integer.parseInt(request.getParameter("customerId"));
            response.getWriter().println(new CustomerUtils().read(paramCustomer).get().getOrders());
        }
        if (map.containsKey("id")) {
            var paramId = Integer.parseInt(request.getParameter("id"));
            response.getWriter().println(orderUtils.read(paramId).get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        requestToJSON(request);
        var jsonObject = requestToJSON(request);
        var customerId = jsonObject.getInt("customer_id");
        var orderDate =  LocalDateTime.parse(jsonObject.getString("order_date"));
        var orderNumber = jsonObject.optString("order_number");
        var totalAmount = jsonObject.getBigDecimal("total_amount");
        var productIdList = new ArrayList<Integer>();
        JSONArray jArray = jsonObject.getJSONArray("product_list"); //parsing the array of ids
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                productIdList.add(jArray.getJSONObject(i).getInt("product_id"));
            }
        }
        var order = orderUtils.create(orderNumber, customerId, orderDate, totalAmount, productIdList);
        log.info("doPost() - Заказ записан в БД: {} ", order);
        response.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        requestToJSON(request);
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
        var success = orderUtils.update(id, customerIdd, orderNumber, totalAmount);
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
        if (orderUtils.read(id).isEmpty()) {
            response.getWriter().println("Попытка удалить несуществующую запись.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        orderUtils.delete(id);
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
