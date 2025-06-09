package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.DTO.CategoryDTO;
import com.example.ProjectJAVA.Entity.Categories;
import com.example.ProjectJAVA.Payloads.Request.CategoryRequest;

import java.util.List;

public interface CategoryServiceImp {
    public List<CategoryDTO> categoryList();
    public Boolean checkCreateCategory(CategoryRequest categoryRequest);

    public Categories removeCategoryById(int id);

    public Categories updateCategoryById(int id, CategoryRequest categoryRequest);
}
