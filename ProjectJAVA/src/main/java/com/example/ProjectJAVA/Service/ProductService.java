package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.DTO.ProductDTO;
import com.example.ProjectJAVA.Entity.Products;
import com.example.ProjectJAVA.Repository.ProductRepository;
import com.example.ProjectJAVA.Service.Imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements ProductServiceImp {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDTO> getProducts() {
        List<Products> productsList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Products products : productsList){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProduct_id(products.getProduct_id());
            productDTO.setProduct_name(products.getProduct_name());
            productDTO.setProduct_image(products.getProduct_image());
            productDTO.setPrice(products.getPrice());
            productDTO.setDescription(products.getDescription());
            productDTO.setQuantity(products.getQuantity());
            productDTO.setCategory_id(products.getCategories().getCategory_id());

            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public Products getProductById(int product_id) {
        return productRepository.findById(product_id)
                .orElseThrow(() -> new RuntimeException("Can not found product with ID: " + product_id));
    }
}
