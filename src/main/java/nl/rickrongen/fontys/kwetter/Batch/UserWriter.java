package nl.rickrongen.fontys.kwetter.Batch;

import nl.rickrongen.fontys.kwetter.Domain.User;

import javax.batch.api.chunk.ItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by rick on 3/29/17.
 */
@Dependent
@Named("UserWriter")
public class UserWriter implements ItemWriter {
    @PersistenceContext(unitName = "JaeMySQLSource")
    EntityManager em;
    @Override
    public void open(Serializable serializable) throws Exception {
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    @Transactional
    public void writeItems(List<Object> list) throws Exception {
        for (Object o:
            list){
            User u = (User)o;
            em.persist(u);
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
