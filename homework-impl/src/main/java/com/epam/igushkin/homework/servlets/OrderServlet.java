package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.utils.CustomerUtils;
import com.epam.igushkin.homework.utils.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
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
        Order order = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(orderUtils.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            order = orderUtils.read(id).get();
        }
        try {
            response.getWriter().println(order);
        } catch (IOException e) {
            log.error("doGet() - ошибка при попытке считать запись", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
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
        JSONObject jsonObject = null;
        try {
            jsonObject = requestToJSON(request);
        } catch (IOException e) {
            log.error("doPut() - Ошибка при парсинге JSON запроса.", e);
        }
        var id = jsonObject.getInt("id");
        var success = orderUtils.update(id);
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
        JSONObject jsonObject = null;
        try {
            jsonObject = requestToJSON(request);
        } catch (IOException e) {
            log.error("doDelete() - Ошибка при парсинге JSON запроса.", e);
        }
        var id = jsonObject.getInt("id");
        var success = orderUtils.delete(id);
        if (success) {
            response.getWriter().println("The delete was successful.");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter().println("The delete was unsuccessful.");
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }

    private void setContentTypeAndEncoding(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private JSONObject requestToJSON(HttpServletRequest request) throws IOException {
        var inputStream = request.getInputStream();
        var strRequest = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        if (strRequest == "") {
            return null;
        }
        return new JSONObject(strRequest);
    }
}
