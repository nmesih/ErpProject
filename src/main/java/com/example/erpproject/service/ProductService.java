package com.example.erpproject.service;

import com.example.erpproject.databases.entity.OrderDetails;
import com.example.erpproject.databases.entity.Product;
import com.example.erpproject.databases.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByProductId(Long productId) {

        Optional<Product> productOptional = productRepository.findById(productId);

        return productOptional.orElse(null);
    }

    public Product getProductByUUID(UUID uuid) {

        Optional<Product> productOptional = productRepository.findByUuid(uuid);

        return productOptional.orElse(null);
    }

    public Product createProduct(String name, boolean isKdvApplied, Double price,
                                 int stock, List<OrderDetails> orderDetailsList) {

        Product product = new Product();
        product.setName(name);
        product.setKdvApplied(isKdvApplied);
        product.setPrice(price);
        product.setStock(stock);
        product.setOrderDetails(orderDetailsList);

        productRepository.save(product);
        return product;
    }

    @Transactional
    public Product updateProductByUUID(UUID uuid, Product newProduct) {

        Product product = getProductByUUID(uuid);

        if (product != null) {

            product.setName(newProduct.getName());
            product.setKdvApplied(newProduct.isKdvApplied());
            product.setPrice(newProduct.getPrice());
            product.setStock(newProduct.getStock());
            product.setOrderDetails(newProduct.getOrderDetails());

            productRepository.save(product);

            return product;

        } else
            return null;
    }

    public Product updateProductPrice(Long productId, BigDecimal price) {
        Product product = getProductByProductId(productId);
        productRepository.updateProductPrice(price, productId);
        return product;
    }

    @Transactional
    public Boolean deleteProductById(Long id) {
        Product product = getProductByProductId(id);

        if (product != null) {

            productRepository.deleteById(id);

            return true;
        } else {
            return false;
        }

    }
}
