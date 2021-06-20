package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.converters.supplier.DtoToSupplierConverter;
import com.epam.igushkin.homework.converters.supplier.SupplierToDTOConverter;
import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.dto.SupplierDTO;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.resources.SupplierResource;
import com.epam.igushkin.homework.services.impl.SupplierServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SupplierResource.class)
@ContextConfiguration(classes = {
        SupplierServiceImpl.class,
        SupplierResourceImpl.class,
        SupplierToDTOConverter.class,
        DtoToSupplierConverter.class,
        MyExceptionHandler.class
})
public class SupplierResourceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SupplierServiceImpl supplierService;

    @MockBean
    SupplierToDTOConverter supplierToDTOConverter;

    @MockBean
    DtoToSupplierConverter dtoToSupplierConverter;

    @Test
    public void testGetSupplierReturnsSupplier() throws Exception {
        Supplier supplier = getTestSupplier();
        SupplierDTO supplierDTO = getTestDTO();
        when(supplierService.findById(1)).thenReturn(supplier);
        when(supplierToDTOConverter.convert(supplier)).thenReturn(supplierDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/supplier/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.company_name").value("ABC Corp"));
    }

    @Test
    public void testGetAllSuppliersReturnsListOfTwoElements() throws Exception {
        Supplier supplier = getTestSupplier();
        SupplierDTO supplierDTO = getTestDTO();
        List<Supplier> testList = List.of(supplier, supplier);
        when(supplierService.getAll()).thenReturn(testList);
        when(supplierToDTOConverter.convert(supplier)).thenReturn(supplierDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/supplier/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String stringResponse = result.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(stringResponse);
        Assertions.assertEquals(2, jsonArray.length());
    }

    @Test
    public void testGetSupplierReturnsStatus404() throws Exception {
        when(supplierService.findById(1)).thenThrow(new NoEntityFoundException("Из тестов: нет такого поставщика."));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/supplier/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostReturnsSupplier() throws Exception {
        Supplier supplier = getTestSupplier();
        SupplierDTO supplierDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(supplierDTO);
        when(dtoToSupplierConverter.convert(supplierDTO)).thenReturn(supplier);
        when(supplierService.save(supplier)).thenReturn(supplier);
        when(supplierToDTOConverter.convert(supplier)).thenReturn(supplierDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company_name").value("ABC Corp"));
    }

    @Test
    public void testDeleteReturnsTrue() throws Exception {
        when(supplierService.delete(1)).thenReturn(true);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/supplier/1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteReturnsFalse() throws Exception {
        when(supplierService.delete(1)).thenReturn(false);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/supplier/1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("false", result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateSupplierReturnsSupplier() throws Exception {
        Supplier supplier = getTestSupplier();
        SupplierDTO supplierDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(supplierDTO);
        when(dtoToSupplierConverter.convert(supplierDTO)).thenReturn(supplier);
        when(supplierService.update(supplier)).thenReturn(supplier);
        when(supplierToDTOConverter.convert(supplier)).thenReturn(supplierDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/supplier/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company_name").value("ABC Corp"));
    }

    @Test
    public void testUpdateSupplierReturnsStatus404() throws Exception {
        Supplier supplier = getTestSupplier();
        SupplierDTO supplierDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(supplierDTO);
        when(dtoToSupplierConverter.convert(supplierDTO)).thenReturn(supplier);
        when(supplierService.update(supplier)).thenThrow(new NoEntityFoundException("Из тестов: такого поставщика нет."));
        mockMvc.perform(
                MockMvcRequestBuilders.put("/supplier/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }


    private SupplierDTO getTestDTO() {
        return new SupplierDTO()
                .setSupplierId(1)
                .setCompanyName("ABC Corp")
                .setPhone("+7-55555");
    }

    private Supplier getTestSupplier() {
        return new Supplier()
                .setSupplierId(1)
                .setCompanyName("ABC Corp")
                .setPhone("+7-55555");
    }

}
