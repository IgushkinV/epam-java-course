package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.services.impl.SupplierCRUDServiceImpl;
import com.epam.igushkin.homework.servlets.utils.ServletUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Service
@Slf4j
@RequiredArgsConstructor
public class SupplierServlet extends HttpServlet {

    private final SupplierCRUDServiceImpl supplierService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        Supplier supplier = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(supplierService.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            supplier = supplierService.read(id).get();
        }
        try {
            response.getWriter().println(supplier);
        } catch (IOException e) {
            log.error("doGet() - ошибка при попытке считать запись", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        var createdSupplier = supplierService.create(jsonObject);
        if (createdSupplier.isPresent()) {
            log.info("doPost() - Поставщик записан: {} ", createdSupplier.get());
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            log.info("doPost() - Ошибка при сохранении нового постащика.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        var id = Integer.parseInt(request.getParameter("id"));
        var updatedSupplier = supplierService.update(id, jsonObject);
        if (updatedSupplier.isPresent()) {
            log.info("Обновление поставщика № {} прошло успешно", id);
            log.debug("Поставщик № {} после обновления: {}", id, updatedSupplier.get());
            response.getWriter().println(updatedSupplier.get());
        } else {
            log.info("Не удалось обновить поставщика № {}", id);
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        var result = supplierService.delete(id);
        response.getWriter().println("Результат удаления поставщика № " + id + ": " + result);
    }
}
