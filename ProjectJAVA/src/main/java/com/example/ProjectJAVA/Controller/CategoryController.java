package com.example.ProjectJAVA.Controller;


import com.example.ProjectJAVA.Entity.Categories;
import com.example.ProjectJAVA.Payloads.Request.CategoryRequest;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.CategoryServiceImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    CategoryServiceImp categoryServiceImp;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCategory(){
        return new ResponseEntity<>(categoryServiceImp.categoryList(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewCategory(@RequestBody CategoryRequest categoryRequest){
        ResponseData responseData = new ResponseData();

        boolean isSuccess = categoryServiceImp.checkCreateCategory(categoryRequest);

        if (isSuccess){
            responseData.setSuccess(true);
            responseData.setData("success!!!!!!!!!!!!!!!!!!!");
        }else {
            responseData.setSuccess(false);
            responseData.setData("fail!!!!!!!!!!!!!!!!!!!");
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/get/{category_id}")
    public ResponseEntity getCategoryById(@PathVariable int category_id){
        ResponseData responseData = new ResponseData();
        Categories categories = categoryServiceImp.getCategoryById(category_id);
        responseData.setData(categories);
        return new ResponseEntity(responseData, HttpStatus.OK);
    }

    @DeleteMapping("delete/{category_id}")
    public ResponseEntity<?> removeCategory(@PathVariable Integer category_id){
        ResponseData responseData = new ResponseData();
        try {
            System.out.println("Anhduy");
            Categories categories = categoryServiceImp.removeCategoryById(category_id);
            return new ResponseEntity<>(responseData,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(responseData,HttpStatus.NOT_FOUND);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(responseData,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @PutMapping("/update/{category_id}")
//    public ResponseEntity<?> updateCategory(@PathVariable Integer category_id, @RequestBody CategoryRequest categoryRequest){
//        ResponseData responseData = new ResponseData();
//        return new ResponseEntity<>(responseData, HttpStatus.OK);
//    }
}
