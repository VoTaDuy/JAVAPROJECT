package com.example.ProjectJAVA.Controller;


import com.example.ProjectJAVA.Entity.Categories;
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
                                           @RequestParam int price,
                                           @RequestParam int category_id){

        ResponseData responseData = new ResponseData();
        boolean isSuccess = productServiceImp.createProduct(file,product_name,description,quantity,price,category_id);

        System.out.println(isSuccess);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

}
