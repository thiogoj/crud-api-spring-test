package joaquinthiogo.crud;

import jakarta.persistence.*;
import joaquinthiogo.crud.entity.Product;
import joaquinthiogo.crud.repository.ProductRepository;
import joaquinthiogo.crud.repository.ProductRepositoryImpl;
import joaquinthiogo.crud.util.JpaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductRepositoryTest {


    @Test
    void insertProduct() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Product product = new Product();
        product.setName("Samsung");
        product.setPrice(2_000_000L);
        product.setDescription("Ini samsung keren");

        entityManager.persist(product);

        transaction.commit();
        entityManager.close();
    }

    @Test
    void getAllProducts() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Product> products = entityManager.createQuery("select p from products p ", Product.class);


        for (Product product : products.getResultList()) {
            System.out.println(product.getName());
            System.out.println(product.getDescription());
            System.out.println(product.getPrice());
        }

        transaction.commit();
        entityManager.close();
    }
}
