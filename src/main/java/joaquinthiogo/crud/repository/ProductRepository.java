package joaquinthiogo.crud.repository;

import joaquinthiogo.crud.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepository {

    List<Product> getAllProducts();

    Product getProductById(Integer id);

    void insertProduct(Product product);

    void updateProduct();

    void deleteProduct();

}
