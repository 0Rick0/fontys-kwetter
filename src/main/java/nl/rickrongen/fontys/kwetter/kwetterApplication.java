package nl.rickrongen.fontys.kwetter;

import nl.rickrongen.fontys.kwetter.RestApi.KwetterApi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rick on 3/8/17.
 */
@ApplicationPath("/")
public class kwetterApplication extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add( KwetterApi.class );
        return h;
    }
}
