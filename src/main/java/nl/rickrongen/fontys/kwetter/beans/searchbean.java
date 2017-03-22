package nl.rickrongen.fontys.kwetter.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by rick on 3/20/17.
 */
@Named("search")
@RequestScoped
public class searchbean {
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void executeSearch(){
        //todo search
    }
}
