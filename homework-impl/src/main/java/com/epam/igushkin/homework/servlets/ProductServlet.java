package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.utils.ProductUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ProductServlet extends HttpServlet {

    private final ProductUtils productUtils = new ProductUtils();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        Product product = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(productUtils.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            product = productUtils.read(id).get();
        }
        try {
            response.getWriter().println(product);
        } catch (IOException e) {
            log.error("doGet() - ошибка при попытке считать запись", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var jsonObject = requestToJSON(request);
        var name = jsonObject.getString("product_name");
        var unitPrice = jsonObject.getBigDecimal("unit_price");
        var supplierId = jsonObject.getInt("supplier_id");
        var isDiscontinued = jsonObject.getBoolean("is_discontinued");
        var product = productUtils.create(name, supplierId, unitPrice, isDiscontinued);
        log.info("doPost() - Создан и записан в бд Product {}", product);
        response.getWriter().println(product);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var jsonObject = requestToJSON(request);
        var id = Integer.parseInt(request.getParameter("id"));
        var productName = jsonObject.getString("product_name");
        var supplierId = jsonObject.getInt("supplier_id");
        var unitPrice = jsonObject.getBigDecimal("unit_price");
        var isDiscontinued = jsonObject.getBoolean("is_discontinued");
        productUtils.update(id, productName, supplierId, unitPrice, isDiscontinued);
        response.getWriter().println(productUtils.read(id).get());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        if (productUtils.read(id).isEmpty()) {
            response.getWriter().println("Попытка удалить несуществующую запись.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        productUtils.delete(id);
        response.getWriter().println("Product № " + id + " удалён.");
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
