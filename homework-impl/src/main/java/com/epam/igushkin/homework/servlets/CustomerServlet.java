package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.utils.CustomerUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
public class CustomerServlet extends HttpServlet {

    private final CustomerUtils customerUtils = new CustomerUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        setContentTypeAndEncoding(response);
        Customer customer = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(customerUtils.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            customer = customerUtils.read(id).get();
        }
        try {
            response.getWriter().println(customer);
        } catch (IOException e) {
            log.error("doGet() - ошибка при попытке считать запись", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        var jsonObject = requestToJSON(request);
        var name = jsonObject.getString("customer_name");
        var phone = jsonObject.getString("phone");
        var customer = customerUtils.create(name, phone);
        log.info("doPost() - Создан и записан в бд Customer {}", customer);
        response.getWriter().println(customer);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        var jsonObject = requestToJSON(request);
        var id = Integer.parseInt(request.getParameter("id"));
        var customerName = jsonObject.getString("customer_name");
        var phone = jsonObject.getString("phone");
        customerUtils.update(id, customerName, phone);
        response.getWriter().println(customerUtils.read(id).get());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        if (customerUtils.read(id).isEmpty()) {
            response.getWriter().println("Попытка удалить несуществующую запись.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        customerUtils.delete(id);
        response.getWriter().println("Customer № " + id + " удалён.");
    }

    private void setContentTypeAndEncoding(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    private JSONObject requestToJSON(HttpServletRequest request) throws IOException {
        var inputStream = request.getInputStream();
        var strRequest = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return new JSONObject(strRequest);
    }
}
