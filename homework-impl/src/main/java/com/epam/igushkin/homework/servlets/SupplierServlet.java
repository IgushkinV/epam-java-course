package com.epam.igushkin.homework.servlets;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.dto.SupplierDTO;
import com.epam.igushkin.homework.exceptions.MyServiceException;
import com.epam.igushkin.homework.services.impl.SupplierServiceImpl;
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
public class SupplierServlet extends HttpServlet {

    private final SupplierServiceImpl supplierService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        ServletUtils.setContentTypeAndEncoding(response);
        SupplierDTO readSupplierDTO = null;
        if (request.getParameterMap().size() == 0) {
            try {
                response.getWriter().println(supplierService.readAll());
                return;
            } catch (IOException e) {
                log.error("doGet() - ошибка при попытке считать все записи", e);
            }
        } else {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                readSupplierDTO = supplierService.read(id);
                response.getWriter().println(readSupplierDTO);
            } catch (IOException | MyServiceException e) {
                log.error("doGet() - ошибка при попытке считать запись", e);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        var newSupplierDTO = SupplierDTO.builder()
                .companyName(jsonObject.getString("company_name"))
                .phone(jsonObject.getString("phone"))
                .build();
        try {
        var createdSupplier = supplierService.create(newSupplierDTO);
            log.info("doPost() - Поставщик записан: {} ", createdSupplier);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (MyServiceException e){
            log.info("doPost() - Ошибка при сохранении нового постащика.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var jsonObject = ServletUtils.requestToJSON(request);
        var id = Integer.parseInt(request.getParameter("id"));
        var companyName = jsonObject.getString("company_name");
        var phone = jsonObject.getString("phone");
        var newSupplierDTO = SupplierDTO.builder()
                .supplierId(id)
                .companyName(companyName)
                .phone(phone)
                .build();
        try {
            var updatedSupplier = supplierService.update(newSupplierDTO);
            log.info("Обновление поставщика № {} прошло успешно", id);
            log.debug("Поставщик № {} после обновления: {}", id, updatedSupplier);
            response.getWriter().println(updatedSupplier);
        } catch (MyServiceException e) {
            log.error("doPut() - Не удалось обновить поставщика № {}", id, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setContentTypeAndEncoding(response);
        var id = Integer.parseInt(request.getParameter("id"));
        var result = supplierService.delete(id);
        response.getWriter().println("Результат удаления поставщика № " + id + ": " + result);
    }
}
