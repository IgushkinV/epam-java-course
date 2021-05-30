package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.utils.SupplierUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class SupplierServlet extends HttpServlet {

    private final SupplierUtils supplierUtils = new SupplierUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setContentTypeAndEncoding(response);
        JSONObject jsonObject = null;
        try {
            jsonObject = requestToJSON(request);
        } catch (IOException e) {
            log.error("doGet() - Ошибка при парсинге JSON запроса.", e);
        }
        if (jsonObject.has("id")) {
            var id = jsonObject.getInt("id");
            var supplierOpt = supplierUtils.read(id);
            if (supplierOpt.isPresent()) {
                response.getWriter().println(supplierOpt.get());
                response.setStatus(HttpServletResponse.SC_OK);
            } else
                response.getWriter().println("Запись не найдена.");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.getWriter().println(supplierUtils.readAll());
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
        JSONObject jsonObject = null;
        try {
            jsonObject = requestToJSON(request);
        } catch (IOException e) {
            log.error("doPut() - Ошибка при парсинге JSON запроса.", e);
        }
        var id = jsonObject.getInt("id");
        var success = supplierUtils.update(id);
        if (success) {
            response.getWriter().println("The update was successful.");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentTypeAndEncoding(response);
        JSONObject jsonObject = null;
        try {
            jsonObject = requestToJSON(request);
        } catch (IOException e) {
            log.error("doDelete() - Ошибка при парсинге JSON запроса.", e);
        }
        var id = jsonObject.getInt("id");
        var success = supplierUtils.delete(id);
        if (success) {
            response.getWriter().println("The delete was successful.");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
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
        return new JSONObject(strRequest);
    }
}
