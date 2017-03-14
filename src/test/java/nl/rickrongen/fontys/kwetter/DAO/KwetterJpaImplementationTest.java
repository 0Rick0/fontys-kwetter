package nl.rickrongen.fontys.kwetter.DAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by rick on 2/22/17.
 */
class KwetterJpaImplementationTest extends IKwetterDaoTest<KwetterJpaImplementation> {
    @Override
    public KwetterJpaImplementation createInstance() {
        return new KwetterJpaImplementation();
    }

    @Override
    public void flush() {
        em.flush();
    }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JaeMySQLTestSource");
    EntityManager em;
    EntityTransaction transaction;
    @BeforeEach
    private void BeforeTest(){
        ((KwetterJpaImplementation)dao).context = em = emf.createEntityManager();
        transaction = ((KwetterJpaImplementation)dao).context.getTransaction();
        transaction.begin();
    }

    @AfterEach
    private void AfterTest(){
        transaction.rollback();//dont want to keep
    }
}