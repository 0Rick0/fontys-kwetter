package nl.rickrongen.fontys.kwetter.beans;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
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

    private String localeCode;

    private static Map<String, String> countries;

    static {
        countries = new HashMap<>();
        countries.put("English (United States)", "en_us");
        countries.put("Dutch (Netherlands)", "nl_nl");
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public String getLocaleCode() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public void countryLocaleCodeChanged(ValueChangeEvent e) {
        Locale lc = new Locale(e.getNewValue().toString());
        setLocaleCode(lc.toString());
        FacesContext.getCurrentInstance().getViewRoot().setLocale(lc);
    }
}
