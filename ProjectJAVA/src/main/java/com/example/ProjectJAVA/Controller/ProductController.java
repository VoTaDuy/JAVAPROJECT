package com.example.ProjectJAVA.Controller;


import com.example.ProjectJAVA.Entity.Products;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImp productServiceImp;


    @GetMapping("/get")
    public ResponseEntity<?> getAllProduct(){

        return new ResponseEntity<>(productServiceImp.getProducts(), HttpStatus.OK);
    }


    @GetMapping("/get/{product_id}")
    public ResponseEntity<?> getProductById(@PathVariable int product_id){
        ResponseData responseData = new ResponseData();
        Products products = productServiceImp.getProductById(product_id);
        responseData.setData(products);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
