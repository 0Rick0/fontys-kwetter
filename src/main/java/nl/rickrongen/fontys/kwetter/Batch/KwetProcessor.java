package nl.rickrongen.fontys.kwetter.Batch;

import nl.rickrongen.fontys.kwetter.Domain.User;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by rick on 3/29/17.
 */
@Dependent
@Named("KwetProcessor")
public class KwetProcessor implements ItemProcessor{
    @Inject
    private KwetterService service;

    @Override
    public Object processItem(Object o) throws Exception {
        String[] parts = ((String)o).split(";",2);
        User u = service.getUser(parts[0]);
        return new Object[]{u, parts[1]};
    }
}
