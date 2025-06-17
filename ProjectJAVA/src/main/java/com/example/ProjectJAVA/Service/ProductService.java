package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.DTO.CategoryDTO;
import com.example.ProjectJAVA.DTO.ProductDTO;
import com.example.ProjectJAVA.Entity.Categories;
import com.example.ProjectJAVA.Entity.Products;
import com.example.ProjectJAVA.Repository.CategoryRepository;
import com.example.ProjectJAVA.Repository.ProductRepository;
import com.example.ProjectJAVA.Service.Imp.FileServiceImp;
import com.example.ProjectJAVA.Service.Imp.ProductServiceImp;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements ProductServiceImp {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    FileServiceImp fileServiceImp;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> getProducts() {
        List<Products> productsList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Products products : productsList){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProduct_id(products.getProductId());
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

    @Override
    public Boolean createProduct(MultipartFile file,
                                 String product_name,
                                 String description,
                                 int quantity,
                                 int price, int category_id) {
        boolean isCreateSuccess = false;

        try {
            boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
            if (isSaveFileSuccess){
                Categories categories = categoryRepository.findById(category_id)
                        .orElseThrow(() -> new RuntimeException("Can not found category for ID " + category_id));
                Products products = new Products();
                products.setProduct_name(product_name);
                products.setDescription(description);
                products.setProduct_image(file.getOriginalFilename());
                products.setPrice(price);
                products.setQuantity(quantity);
                products.setCategories(categories);
                productRepository.save(products);
                isCreateSuccess = true;
            }
        }catch (Exception e){
            System.out.println("Error" + e.getMessage());
            return false;
        }
        return isCreateSuccess;
    }


}
