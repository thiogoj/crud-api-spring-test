package joaquinthiogo.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ConnectionTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void testConnection() {



    }
}
