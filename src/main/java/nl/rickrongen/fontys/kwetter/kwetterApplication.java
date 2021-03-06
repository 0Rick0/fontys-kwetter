package nl.rickrongen.fontys.kwetter;

import nl.rickrongen.fontys.kwetter.RestApi.AllowCORS;
import nl.rickrongen.fontys.kwetter.RestApi.CustomUnauth;
import nl.rickrongen.fontys.kwetter.RestApi.KwetterKwetsApi;
import nl.rickrongen.fontys.kwetter.RestApi.KwetterUserApi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Created by rick on 3/8/17.
 */
@ApplicationPath("/api/")
public class kwetterApplication extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        //add apis
        h.add( KwetterUserApi.class );
        h.add( KwetterKwetsApi.class );

        //CORS filter
        h.add(AllowCORS.class );
        h.add(CustomUnauth.class);

        //add jackson
        h.add(JacksonFeature.class);
        return h;
    }
}
