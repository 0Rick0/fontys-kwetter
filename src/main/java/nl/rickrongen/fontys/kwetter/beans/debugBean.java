package nl.rickrongen.fontys.kwetter.beans;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
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
}
