package nl.rickrongen.fontys.kwetter.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by rick on 3/27/17.
 */
@Named("locale")
@SessionScoped
public class LocaleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale;

    private static Map<String, String> countries;

    static {
        countries = new HashMap<>();
        countries.put("English (United States)", "en_us");
        countries.put("Dutch (Netherlands)", "nl_nl");
    }

    @PostConstruct
    public void init() {
        if(locale != null){
            FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        }else{
        }
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public String getLocaleCode() {
        return locale == null ? null : locale.toString();
    }

    public void setLocaleCode(String localeCode){
        locale = new Locale(localeCode);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        System.out.println("locale updated to " + locale.toString());
    }

    public Locale getLocale(){
        return locale;
    }
}
