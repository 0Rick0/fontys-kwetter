package nl.rickrongen.fontys.kwetter.Batch;

import nl.rickrongen.fontys.kwetter.Domain.User;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 * Created by rick on 3/29/17.
 */
@Dependent
@Named("UserProcessor")
public class UserProcessor implements ItemProcessor{
    @Override
    public Object processItem(Object o) throws Exception {
        String[] parts = ((String)o).split(";", 7);
        return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], null, null, null, null, null, null);
    }
}
