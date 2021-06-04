package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.repository.IRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServlet extends HttpServlet {

    private final IRepository<Customer> customerRepository;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        setContentTypeAndEncoding(response);
        Customer customer = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(customerRepository.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            customer = customerRepository.read(id).get();
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
        var customer = new Customer();
        customer.setCustomerName(name);
        if (Objects.nonNull(phone)) {
            customer.setPhone(phone);
        }
        customerRepository.create(customer); //Нужно ли получать обратно?
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
        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setCustomerName(customerName);
        customer.setPhone(phone);
        customerRepository.update(customer);
        response.getWriter().println(customerRepository.read(id).get());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        if (customerRepository.read(id).isEmpty()) {
            response.getWriter().println("Попытка удалить несуществующую запись.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        customerRepository.delete(id);
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
