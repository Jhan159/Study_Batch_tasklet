package io.springbatch.springbatchlecture.Job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomJobParamIncrementer implements org.springframework.batch.core.JobParametersIncrementer {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmmss");

    @Override
    public JobParameters getNext(JobParameters jobParameters) {

        String id = format.format(new Date());

        return new JobParametersBuilder()
                .addString("run.id", id)
                .toJobParameters();
    }
}
