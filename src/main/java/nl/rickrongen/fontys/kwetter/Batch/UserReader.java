package nl.rickrongen.fontys.kwetter.Batch;

import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Created by rick on 3/28/17.
 */
@Dependent
@Named("UserReader")
public class UserReader implements ItemReader, Serializable {

    @Inject
    private JobContext jobctx;

    private String filename;
    private ItemNumberCheckpoint checkpoint;
    private transient BufferedReader reader;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        filename = jobctx.getProperties().getProperty("userfile");
        reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)));
        if(checkpoint!=null){
            this.checkpoint = checkpoint instanceof ItemNumberCheckpoint ? (ItemNumberCheckpoint)checkpoint : new ItemNumberCheckpoint();
            for (int i = 0; i < this.checkpoint.getIndex(); i++)
                reader.readLine();
        }else{
            this.checkpoint = new ItemNumberCheckpoint();
        }
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }


    @Override
    public Object readItem() throws Exception {
        String line = reader.readLine();
        if(line!= null){
            checkpoint.nextIndex();
            return line;
        }else{
            return null;
        }

    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return checkpoint;
    }
}
