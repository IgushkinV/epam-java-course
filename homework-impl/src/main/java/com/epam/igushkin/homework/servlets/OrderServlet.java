package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.services.impl.OrderCRUDServiceImpl;
import com.epam.igushkin.homework.servlets.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServlet extends HttpServlet {


    private final OrderCRUDServiceImpl orderService;

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
