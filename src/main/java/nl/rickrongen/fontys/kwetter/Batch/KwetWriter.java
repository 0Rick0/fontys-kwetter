package nl.rickrongen.fontys.kwetter.Batch;

import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.batch.api.chunk.ItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;

/**
 * Created by rick on 3/29/17.
 */
@Dependent
@Named("KwetWriter")
public class KwetWriter implements ItemWriter {

    @Inject
    private KwetterService service;

    @Override
    public void open(Serializable serializable) throws Exception {
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void writeItems(List<Object> list) throws Exception {
        for (Object o :
                list) {
            Object[] parts = (Object[]) o;
            User u = (User)parts[0];
            String text = (String)parts[1];
            service.postKwet(u, text);
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }
}
