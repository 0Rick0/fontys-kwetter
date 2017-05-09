package nl.rickrongen.fontys.kwetter.RestApi;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rick on 3/8/17.
 */
@XmlRootElement
public class SuccesObject<T> implements Serializable{
    @XmlElement
    private boolean succes;
    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String self = null;
    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String next = null;
    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String previous = null;
    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer results = null;
    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> extra = null;
    @XmlElement
    private T data;

    public SuccesObject(boolean succes, T data) {
        this.succes = succes;
        setData(data);
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Integer getResults() {
        return results;
    }

    public void setResults(Integer results) {
        this.results = results;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    public void addExtra(String[] keyValue, UriBuilder base){
        if(keyValue.length != 2)
            throw new IllegalArgumentException("keyValue length must be exactly 2");
        base.host(null);
        base.scheme(null);
        base.port(0);
        addExtra(keyValue[0], base.segment(keyValue[1].split("/")).toTemplate().substring(4));
    }

    public void addExtra(String key, String value){
        if(extra == null)
            extra = new HashMap<>();
        extra.put(key, value);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if(data instanceof Collection)
            this.results = ((Collection)data).size();
        this.data = data;
    }
}
