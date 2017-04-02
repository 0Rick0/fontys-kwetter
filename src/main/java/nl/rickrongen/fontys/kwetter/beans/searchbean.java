package nl.rickrongen.fontys.kwetter.beans;

import nl.rickrongen.fontys.kwetter.Domain.Kwet;
import nl.rickrongen.fontys.kwetter.Service.KwetterService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static nl.rickrongen.fontys.kwetter.Util.tryParseInt;

/**
 * Created by rick on 3/20/17.
 */
@Named("search")
@RequestScoped
public class searchbean {
    @Inject
    private KwetterService service;

    private String query;
    private List<Kwet> results;

    public List<Kwet> getResults() {
        getQuery();
        if (results == null)
            search();
        return results;
    }

    public void search() {
        Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int start = 0, count = 10;
        if (requestParameters.containsKey("start"))
            start = tryParseInt(requestParameters.get("start"), start);
        if (requestParameters.containsKey("count"))
            count = tryParseInt(requestParameters.get("count"), count);

        results = service.searchKwets(getQuery(), start, count);
        System.out.println("searched" + results.size());
    }

    public String getQuery() {
        if (query == null) {
            Map<String, String> requestParameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            if (!requestParameters.containsKey("query")){
                results = null;
                return query = "";
            }
            query = requestParameters.get("query");
        }
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String go() {
        try {
            return "search?faces-redirect=true&query=" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
