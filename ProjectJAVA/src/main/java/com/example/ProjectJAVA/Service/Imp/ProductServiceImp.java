package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.DTO.ProductDTO;
import com.example.ProjectJAVA.Entity.Products;

import java.util.List;

public interface ProductServiceImp {

    List<ProductDTO> getProducts();

    Products getProductById(int product_id);
}
