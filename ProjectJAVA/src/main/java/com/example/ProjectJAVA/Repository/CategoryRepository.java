package com.example.ProjectJAVA.Repository;

import com.example.ProjectJAVA.Entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findAll();
    Categories findCategoryByName(String name);
}
