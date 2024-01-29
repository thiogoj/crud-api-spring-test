package joaquinthiogo.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.crud.entity.Product;
import joaquinthiogo.crud.repository.ProductRepository;
import joaquinthiogo.crud.repository.ProductRepositoryImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ObjectMapper objectMapper;
    private ProductRepository productRepository = new ProductRepositoryImpl();

    @GetMapping(path = "/hello")
    @ResponseBody
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping(path = "/api/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> insertProduct(@RequestBody String requestBody) throws JsonProcessingException {
        Product product = objectMapper.readValue(requestBody, Product.class);
        productRepository.insertProduct(product);

        return ResponseEntity.ok(objectMapper.writeValueAsString(product));
    }

    @GetMapping(path = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllProduct() throws JsonProcessingException {
        List<Product> allProducts = productRepository.getAllProducts();

        return ResponseEntity.ok(objectMapper.writeValueAsString(allProducts));
    }

    @SneakyThrows
    @GetMapping(path = "/api/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getProductByid(@PathVariable(name = "id") Integer productId) {
        Product product = productRepository.getProductById(productId);
        return product != null ? ResponseEntity.ok(objectMapper.writeValueAsString(product)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.writeValueAsString(Map.of(
                "error", "Product not found"
        )));
    }

}
