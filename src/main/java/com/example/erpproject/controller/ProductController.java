package com.example.erpproject.controller;
import com.example.erpproject.databases.entity.Product;
import com.example.erpproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProductList() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductByProductId(id), HttpStatus.OK);
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<Integer> getProductStockById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductByProductId(id).getStock(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Product> createPerson(@RequestBody Product product) {
        Product product1 = productService.createProduct(product.getName(), product.isKdvApplied(),
                product.getPrice(), product.getStock(), product.getOrderDetails());

        return new ResponseEntity<>(product1, HttpStatus.CREATED);
    }

    @PutMapping("update/{uuid}")
    public ResponseEntity<Product> updateProductByUuid(@PathVariable UUID uuid, @RequestBody Product newProduct){
        Product product = productService.updateProductByUUID(uuid, newProduct);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/price/{price}/id/{id}")
    public ResponseEntity<Product> updateProductPriceByUuid(@PathVariable BigDecimal price, @PathVariable Long id){
        Product product = productService.updateProductPrice(id, price);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return new ResponseEntity<>("Product başarıyla silindi", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product bulunamadı", HttpStatus.NOT_FOUND);
        }
    }
}
