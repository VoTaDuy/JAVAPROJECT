package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.Entity.Products;
import com.example.ProjectJAVA.Service.Imp.ProductServiceImp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductServiceImp {
    @Override
    public List<Products> getProducts() {
        return List.of();
    }
}
