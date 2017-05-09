package nl.rickrongen.fontys.kwetter.RestApi;

import javax.security.auth.login.LoginException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by rick on 4/10/17.
 */
@Provider
public class CustomUnauth implements ExceptionMapper<LoginException>{
    @Override
    public Response toResponse(LoginException e) {
        Response.ResponseBuilder status = Response.status(401);
        status.header("Access-Control-Allow-Origin", "*");
        status.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        status.header("Access-Control-Allow-Credentials", "true");
        status.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        status.header("Access-Control-Max-Age", "1209600");
        return status.build();
    }
}
