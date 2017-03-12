package nl.rickrongen.fontys.kwetter;

import nl.rickrongen.fontys.kwetter.RestApi.KwetterApi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Created by rick on 3/8/17.
 */
@ApplicationPath("/app/")
public class kwetterApplication extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        //add apis
        h.add( KwetterApi.class );

        //add jackson
        h.add(JacksonFeature.class);
        return h;
    }
}
