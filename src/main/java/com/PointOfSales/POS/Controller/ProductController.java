package com.PointOfSales.POS.Controller;


import com.PointOfSales.POS.DTO.AddProductReqDTO;
import com.PointOfSales.POS.Entity.Product;
import com.PointOfSales.POS.Service.Impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/Allproduct")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> add(@RequestBody AddProductReqDTO dto) {
        System.out.println(1);
        try {
            Product products = productService.addProduct(dto);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{barcode}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer barcode) {
        try {
            String deletionResult = productService.deleteProduct(barcode);
            if (deletionResult.startsWith("Product deleted")) {
                return new ResponseEntity<>(deletionResult, HttpStatus.OK);
            } else if (deletionResult.equals("Product not found")) {
                return new ResponseEntity<>(deletionResult, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(deletionResult, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Handle exceptions and return an appropriate HTTP status code
            return new ResponseEntity<>("Error deleting product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/{barcode}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer barcode,
                                                 @RequestBody AddProductReqDTO dto) {
        try {
            Product updatedProduct = productService.updateProduct(barcode, dto);
            if (updatedProduct != null) {
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions and return an appropriate HTTP status code
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/products/{barcode}")
    public ResponseEntity<Product> patchProduct(@PathVariable Integer barcode, @RequestBody AddProductReqDTO dto) {
        try {
            Product updatedProduct = productService.patchProduct(barcode, dto);
            if (updatedProduct != null) {
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions and return an appropriate HTTP status code
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
