package io.springbatch.springbatchlecture.Job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;

public class CustomJobParamValidator implements org.springframework.batch.core.JobParametersValidator {

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        if(jobParameters.getString("name") == null){
            throw new JobParametersInvalidException("name param is null");
        }
    }
}
