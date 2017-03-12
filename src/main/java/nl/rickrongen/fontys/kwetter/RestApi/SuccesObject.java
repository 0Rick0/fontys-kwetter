package nl.rickrongen.fontys.kwetter.RestApi;

import java.io.Serializable;

/**
 * Created by rick on 3/8/17.
 */
public class SuccesObject<T> implements Serializable{
    private boolean succes;
    private T data;

    public SuccesObject(boolean succes, T data) {
        this.succes = succes;
        this.data = data;
    }

    public boolean isSucces() {
        return succes;
    }

    public T getData() {
        return data;
    }
}
