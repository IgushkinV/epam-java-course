package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.utils.SupplierUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SupplierServlet extends HttpServlet {

    private final SupplierUtils supplierUtils = new SupplierUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        Supplier supplier = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(supplierUtils.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            supplier = supplierUtils.read(id).get();
        }
        try {
            response.getWriter().println(supplier);
        } catch (IOException e) {
            log.error("doGet() - ошибка при попытке считать запись", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var jsonObject = requestToJSON(request);
        var supplierName = jsonObject.optString("company_name");
        var phone = jsonObject.optString("phone");
        var supplier = supplierUtils.create(supplierName, phone);
        log.info("doPost() - Поставщик записан в БД: {} ", supplier);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var jsonObject = requestToJSON(request);
        var id = Integer.parseInt(request.getParameter("id"));
        var companyName = jsonObject.getString("company_name");
        var phone = jsonObject.getString("phone");
        supplierUtils.update(id, companyName, phone);
        response.getWriter().println(supplierUtils.read(id).get());
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        if (supplierUtils.read(id).isEmpty()) {
            response.getWriter().println("Попытка удалить несуществующую запись.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        supplierUtils.delete(id);
        response.getWriter().println("Supplier № " + id + " удалён.");
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
