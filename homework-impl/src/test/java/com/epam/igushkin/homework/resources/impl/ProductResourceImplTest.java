package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.converters.product.DtoToProductConverter;
import com.epam.igushkin.homework.converters.product.ProductToDTOConverter;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.dto.ProductDTO;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.resources.ProductResource;
import com.epam.igushkin.homework.services.impl.ProductServiceImpl;
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

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductResource.class)
@ContextConfiguration(classes = {
        ProductServiceImpl.class,
        ProductResourceImpl.class,
        ProductToDTOConverter.class,
        DtoToProductConverter.class,
        MyExceptionHandler.class
})
public class ProductResourceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductServiceImpl productService;
    @MockBean
    DtoToProductConverter dtoToProductConverter;
    @MockBean
    ProductToDTOConverter productToDTOConverter;

    @Test
    public void testGetReturnsProduct() throws Exception {
        Product product = getTestProduct();
        ProductDTO productDTO = getTestDTO();
        when(productService.findById(1)).thenReturn(product);
        when(productToDTOConverter.convert(product)).thenReturn(productDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.product_name").value("Name"));
    }

    @Test
    public void testGetProductReturnsStatus404() throws Exception {
        when(productService.findById(1)).thenThrow(new NoEntityFoundException("Из тестов: нет такого продукта."));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/product/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllProductsReturnsListOfTwoElements() throws Exception {
        Product product = getTestProduct();
        ProductDTO productDTO = getTestDTO();
        List<Product> productList = List.of(product, product);
        when(productService.getAll()).thenReturn(productList);
        when(productToDTOConverter.convert(product)).thenReturn(productDTO);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/product/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String stringResponse = result.getResponse().getContentAsString();
        JSONArray array = new JSONArray(stringResponse);
        Assertions.assertEquals(2, array.length());
    }

    @Test
    public void testPostReturnsProduct() throws Exception {
        Product product = getTestProduct();
        ProductDTO productDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(productDTO);
        when(productService.save(product)).thenReturn(product);
        when(dtoToProductConverter.convert(productDTO)).thenReturn(product);
        when(productToDTOConverter.convert(product)).thenReturn(productDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post(("/product/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_name").value("Name"));
    }

    @Test
    public void testDeleteReturnsTrue() throws Exception {
        when(productService.delete(1)).thenReturn(true);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/product/1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteReturnsFalse() throws Exception {
        when(productService.delete(1)).thenReturn(false);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/product/1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("false", result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateReturnsProduct() throws Exception {
        Product product = getTestProduct();
        ProductDTO productDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(productDTO);
        when(dtoToProductConverter.convert(productDTO)).thenReturn(product);
        when(productService.update(product)).thenReturn(product);
        when((productToDTOConverter.convert(product))).thenReturn(productDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_name").value("Name"));
    }

    @Test
    public void testUpdateReturnsStatus404() throws Exception {
        ProductDTO productDTO = getTestDTO();
        Product product = getTestProduct();
        when(dtoToProductConverter.convert(productDTO)).thenReturn(product);
        when(productService.update(product)).thenThrow(new NoEntityFoundException("Из тестов: нет такого продукта."));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(productDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    private ProductDTO getTestDTO() {
        return new ProductDTO()
                .setProductId(1)
                .setSupplierId(1)
                .setProductName("Name")
                .setUnitPrice(BigDecimal.valueOf(100))
                .setDiscontinued(false);
    }

    private Product getTestProduct() {
        return new Product()
                .setProductId(1)
                .setProductName("Name")
                .setDiscontinued(false)
                .setUnitPrice(BigDecimal.valueOf(100))
                .setSupplier(getTestSupplier());
    }

    private Supplier getTestSupplier() {
        return new Supplier()
                .setSupplierId(1)
                .setCompanyName("Company Name")
                .setPhone("+7-77777");
    }
}
