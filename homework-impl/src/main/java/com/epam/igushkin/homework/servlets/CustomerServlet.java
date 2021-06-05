package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.services.CRUDService;
import com.epam.igushkin.homework.servlets.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServlet extends HttpServlet {

    private final CRUDService<Customer> customerService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setContentTypeAndEncoding(response);
        Customer customer = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(customerService.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            customer = customerService.read(id).get();
        }
        try {
            response.getWriter().println(customer);
        } catch (IOException e) {
            log.error("doGet() - ошибка при попытке считать запись", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        var createdCustomer = customerService.create(jsonObject);
        log.info("doPost() - Данные переданы в сервис. Сервис вернул: {}", createdCustomer);
        response.getWriter().println(createdCustomer);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        var id = Integer.parseInt(request.getParameter("id"));
        var updatedCustomer = customerService.update(id, jsonObject);
        response.getWriter().println(updatedCustomer.get());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        var result = customerService.delete(id);
        if (result) {
            response.getWriter().println("Customer № " + id + " удалён.");
        } else {
            response.getWriter().println("Customer № " + id + " - не получилось удалить.");
        }
    }
}
