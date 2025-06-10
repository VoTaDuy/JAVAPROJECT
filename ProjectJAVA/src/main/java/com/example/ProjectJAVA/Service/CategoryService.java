package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.DTO.CategoryDTO;
import com.example.ProjectJAVA.Entity.Categories;
import com.example.ProjectJAVA.Payloads.Request.CategoryRequest;
import com.example.ProjectJAVA.Repository.CategoryRepository;
import com.example.ProjectJAVA.Service.Imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<CategoryDTO> categoryList() {
        List<Categories> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (Categories categories : categoryList ){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategory_id(categories.getCategory_id());
            categoryDTO.setCategory_name(categories.getName());
            categoryDTO.setDescription(categories.getDescription());
            categoryDTOList.add(categoryDTO);
        }
        return  categoryDTOList;
    }

    @Override
    public Categories getCategoryById(int category_id){
        return categoryRepository.findById(category_id)
                .orElseThrow(() -> new RuntimeException("can't not found Category By Id: " + category_id));
    }

    @Override
    public Boolean checkCreateCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.findCategoryByName(categoryRequest.getCategory_name()) != null) {
            System.out.println("Category already exist!!! try another name");
            return false;
        }
        Categories categories = new Categories();
        categories.setName(categoryRequest.getCategory_name());
        categories.setDescription(categoryRequest   .getDescription());
        try{
            System.out.println("Inserting Category");
            categoryRepository.save(categories);
            System.out.println("Inserted Category");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Categories removeCategoryById(int id) {
        Categories categories = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categories not found with ID: " + id ));
        categoryRepository.delete(categories);
        return categories;
    }

    @Override
    public Categories updateCategoryById(int id, CategoryRequest categoryRequests) {
        Categories categories = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
//        if (categories)

        return null;
    }


}
