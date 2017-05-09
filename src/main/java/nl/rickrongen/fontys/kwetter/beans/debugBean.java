package nl.rickrongen.fontys.kwetter.beans;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by rick on 3/29/17.
 */
@SessionScoped
@Named("debug")
public class debugBean implements Serializable {

    private Long createinitId;

    public void startCreateInitJob(){
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        createinitId = jobOperator.start("createinit", null);
    }

    public String getCreateInitStatus(){
        if(createinitId == null)
            return "Not started yet!";
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        return jobOperator.getJobExecution(createinitId).getBatchStatus().toString();
    }

    public void handle401(){
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.addHeader("Access-Control-Max-Age", "1209600");
    }
}
