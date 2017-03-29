package nl.rickrongen.fontys.kwetter.Batch;

import java.io.Serializable;

/**
 * Created by rick on 3/29/17.
 */
public class ItemNumberCheckpoint implements Serializable {
    private long itemcount;
    private long index;

    public ItemNumberCheckpoint(){
        index = 0;
    }

    public ItemNumberCheckpoint(long itemcount){
        this();
        this.itemcount = itemcount;
    }

    public long getItemcount() {
        return itemcount;
    }

    public void setItemcount(long itemcount) {
        this.itemcount = itemcount;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public void nextIndex(){
        index++;
    }
}
