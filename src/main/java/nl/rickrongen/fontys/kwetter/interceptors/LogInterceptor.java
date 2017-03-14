package nl.rickrongen.fontys.kwetter.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Created by rick on 3/14/17.
 */
@Interceptor
@Log
public class LogInterceptor {
    @AroundInvoke
    public Object beforeMethod(InvocationContext ctx) throws Exception {
        System.out.println("Entering " + ctx.getMethod().getDeclaringClass().getName() + "::" + ctx.getMethod().getName());
        return ctx.proceed();
    }
}
