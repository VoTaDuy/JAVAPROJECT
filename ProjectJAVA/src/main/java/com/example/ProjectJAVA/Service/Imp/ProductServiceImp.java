package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.DTO.ProductDTO;
import com.example.ProjectJAVA.Entity.Products;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceImp {

    List<ProductDTO> getProducts();

    Products getProductById(int product_id);

    Boolean createProduct(MultipartFile file, String product_name, String description, int quantity, int price, int category_id);
}
