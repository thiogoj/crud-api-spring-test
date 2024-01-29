package joaquinthiogo.crud.repository;

import jakarta.persistence.*;
import joaquinthiogo.crud.entity.Product;
import joaquinthiogo.crud.util.JpaUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepository{

    @Override
    public List<Product> getAllProducts() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Product> productsQuery = entityManager.createQuery("SELECT p FROM products p", Product.class);
            List<Product> productList = productsQuery.getResultList();
            transaction.commit();

            return productList;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Product getProductById(Integer productId) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product product = entityManager.find(Product.class, productId);
            transaction.commit();

            return product;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void insertProduct(Product product) {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setDescription(product.getDescription());

        entityManager.persist(product);

        transaction.commit();
        entityManager.close();
    }

    @Override
    public void updateProduct() {

    }

    @Override
    public void deleteProduct() {

    }
}
