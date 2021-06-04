package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.repository.ipml.ProductRepository;
import com.epam.igushkin.homework.repository.ipml.SupplierRepository;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServlet extends HttpServlet {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        Product product = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(productRepository.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            product = productRepository.read(id).get();
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
        var newProduct = new Product();
        newProduct.setProductName(name);
        newProduct.setUnitPrice(unitPrice);
        newProduct.setDiscontinued(isDiscontinued);
        newProduct.setSupplier(supplierRepository.read(supplierId).get());
        var product = productRepository.create(newProduct);
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
        var updatedProduct = new Product();
        updatedProduct.setProductId(id);
        updatedProduct.setProductName(productName);
        updatedProduct.setSupplier(supplierRepository.read(supplierId).get());
        updatedProduct.setUnitPrice(unitPrice);
        updatedProduct.setDiscontinued(isDiscontinued);
        productRepository.update(updatedProduct);
        response.getWriter().println(productRepository.read(id).get());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        if (productRepository.read(id).isEmpty()) {
            response.getWriter().println("Попытка удалить несуществующую запись.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        productRepository.delete(id);
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
