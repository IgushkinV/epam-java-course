package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.services.impl.ProductServiceImpl;
import com.epam.igushkin.homework.servlets.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j

@RequiredArgsConstructor
public class ProductServlet extends HttpServlet {

    private final ProductServiceImpl productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        Product product = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(productService.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            product = productService.read(id).get();
        }
        try {
            response.getWriter().println(product);
        } catch (IOException e) {
            log.error("doGet() - ошибка при попытке считать запись", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        var createdProduct = productService.create(jsonObject);
        if (createdProduct.isPresent()) {
            log.info("doPost() - Создан и записан Product {}", createdProduct.get());
            response.getWriter().println(createdProduct.get());
        } else {
            log.info("doPost() - Ошибка во время записи Product.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        var id = Integer.parseInt(request.getParameter("id"));
        var updatedProduct = productService.update(id, jsonObject);
        response.getWriter().println(updatedProduct.get());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        var result = productService.delete(id);
        if (result) {
            response.getWriter().println("Product № " + id + " удалён.");
        } else {
            response.getWriter().println("Product № " + id + " - не получилось удалить.");
        }
    }
}
