package com.example.ProjectJAVA.Controller;


import com.example.ProjectJAVA.Entity.Products;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.FileServiceImp;
import com.example.ProjectJAVA.Service.Imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


@CrossOrigin("*")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImp productServiceImp;

    @Autowired
    FileServiceImp fileServiceImp;

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

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFileProduct(@PathVariable String filename){
        Resource resource = fileServiceImp.loadFile(filename);

        //Check null
        if (resource == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("File not exist: " + filename);
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION
                , "attachment; filename=\""
                + resource.getFilename() + "\"").body(resource);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestParam MultipartFile file,
                                           @RequestParam String product_name,
                                           @RequestParam String description,
                                           @RequestParam int quantity,
                                           @RequestParam BigDecimal price,
                                           @RequestParam int category_id){

        ResponseData responseData = new ResponseData();
        boolean isSuccess = productServiceImp.createProduct(file,product_name,description,quantity,price,category_id);

        System.out.println(isSuccess);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

    @PutMapping("/update/{product_id}/full")
    public ResponseEntity<?> updateProduct(@RequestParam MultipartFile file,
                                           @RequestParam String product_name,
                                           @RequestParam String description,
                                           @RequestParam int quantity,
                                           @RequestParam BigDecimal price,
                                           @PathVariable Integer product_id){
        ResponseData responseData = new ResponseData();
        try {

            boolean products = productServiceImp.updateProduct(file,product_name, description, quantity, price, product_id);
            responseData.setDesc("Update product successfully!");
            responseData.setData(products);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData.setDesc("Update product failed!");
            return new ResponseEntity<>(responseData , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{product_id}")
    public ResponseEntity<?> updateProductQuantity(@PathVariable Integer product_id,@RequestParam int quantity){
        ResponseData responseData = new ResponseData();
        try {
            productServiceImp.updateProductQuantity(product_id,quantity);
            responseData.setData("Update quantity successfully!");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData.setData("Update quantity failed!");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer product_id){
        ResponseData responseData = new ResponseData();
        try {
            productServiceImp.removeProductById(product_id);
            responseData.setData("Delete product successfully!");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData.setData("Delete product failed!");
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
