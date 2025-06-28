package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.DTO.ProductDTO;
import com.example.ProjectJAVA.Entity.Products;
import com.example.ProjectJAVA.Payloads.Request.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface ProductServiceImp {

    List<ProductDTO> getProducts();

    Products getProductById(int product_id);

    Boolean createProduct(MultipartFile file, String product_name, String description, int quantity, BigDecimal price, int category_id);

    Products updateProduct(ProductRequest productRequest, int product_id);

    Products updateProductQuantity(int product_id, int quantity);

    Products removeProductById(int product_id);
}
