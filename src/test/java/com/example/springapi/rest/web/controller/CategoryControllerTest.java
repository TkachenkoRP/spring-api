package com.example.springapi.rest.web.controller;

import com.example.springapi.exception.EntityNotFoundException;
import com.example.springapi.mapper.CategoryMapper;
import com.example.springapi.model.CategoryEntity;
import com.example.springapi.rest.AbstractTestController;
import com.example.springapi.rest.StringTestUtils;
import com.example.springapi.service.CategoryService;
import com.example.springapi.web.model.CategoryEntityListResponse;
import com.example.springapi.web.model.CategoryEntityResponse;
import com.example.springapi.web.model.UpsertCategoryEntityRequest;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

public class CategoryControllerTest extends AbstractTestController {

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    @Test
    public void whenFindAll_thenReturnAllCategories() throws Exception {
        List<CategoryEntity> categories = new ArrayList<>();
        categories.add(createCategory(1L));
        categories.add(createCategory(2L));

        List<CategoryEntityResponse> categoriesResponse = new ArrayList<>();
        categoriesResponse.add(createCategoryResponse(1L));
        categoriesResponse.add(createCategoryResponse(2L));

        CategoryEntityListResponse categoryEntityListResponse = new CategoryEntityListResponse(categoriesResponse);

        Mockito.when(categoryService.findAll()).thenReturn(categories);

        Mockito.when(categoryMapper.entityListToListResponse(categories)).thenReturn(categoryEntityListResponse);

        String actualResponse = mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_all_categories_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).findAll();
        Mockito.verify(categoryMapper, Mockito.times(1)).entityListToListResponse(categories);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenGetCategoryById_thenReturnCategoryById() throws Exception {
        CategoryEntity category = createCategory(1L);
        CategoryEntityResponse categoryResponse = createCategoryResponse(1L);

        Mockito.when(categoryService.findById(1L)).thenReturn(category);
        Mockito.when(categoryMapper.entityToResponse(category)).thenReturn(categoryResponse);

        String actualResponse = mockMvc.perform(get("/api/category/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/find_category_by_id_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).findById(1L);
        Mockito.verify(categoryMapper, Mockito.times(1)).entityToResponse(category);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateCategory_thenReturnNewCategory() throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setName("Category1");
        CategoryEntity createdCategory = createCategory(1L);
        CategoryEntityResponse response = createCategoryResponse(1L);
        UpsertCategoryEntityRequest request = new UpsertCategoryEntityRequest("Category1");

        Mockito.when(categoryService.save(category)).thenReturn(createdCategory);
        Mockito.when(categoryMapper.requestToEntity(request)).thenReturn(category);
        Mockito.when(categoryMapper.entityToResponse(createdCategory)).thenReturn(response);

        String actualResponse = mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils.readStringFromResource("response/create_category_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).save(category);
        Mockito.verify(categoryMapper, Mockito.times(1)).requestToEntity(request);
        Mockito.verify(categoryMapper, Mockito.times(1)).entityToResponse(createdCategory);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateCategory_thenReturnUpdatedCategory() throws Exception {
        UpsertCategoryEntityRequest request = new UpsertCategoryEntityRequest("New Category1");
        CategoryEntity updatedCategory = new CategoryEntity(1L, "New Category1", new ArrayList<>());
        CategoryEntityResponse response = new CategoryEntityResponse(1L, "New Category1", 0);

        Mockito.when(categoryService.update(updatedCategory)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.requestToEntity(1L, request)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.entityToResponse(updatedCategory)).thenReturn(response);

        String actualResponse = mockMvc.perform(put("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectResponse = StringTestUtils.readStringFromResource("response/update_category_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).update(updatedCategory);
        Mockito.verify(categoryMapper, Mockito.times(1)).requestToEntity(1L, request);
        Mockito.verify(categoryMapper, Mockito.times(1)).entityToResponse(updatedCategory);

        JsonAssert.assertJsonEquals(expectResponse, actualResponse);
    }

    @Test
    public void whenDeleteCategoryById_thenReturnStatusNoContent() throws Exception {
        mockMvc.perform(delete("/api/category/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(categoryService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenFindByIdNotExistedCategory_thenReturnError() throws Exception {
        Mockito.when(categoryService.findById(500L)).thenThrow(new EntityNotFoundException("Категория с ID 500 не найдена!"));

        var response = mockMvc.perform(get("/api/category/500"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/find_category_by_id_not_found_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).findById(500L);

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateCategoryWithEmptyName_thenReturnError() throws Exception {
        var response = mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertCategoryEntityRequest())))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource("response/empty_category_name_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }
}
