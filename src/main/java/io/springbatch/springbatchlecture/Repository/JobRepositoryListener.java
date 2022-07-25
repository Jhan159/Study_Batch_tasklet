package io.springbatch.springbatchlecture.Repository;

import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobRepositoryListener implements JobExecutionListener {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("beforeJob executed");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "20220702")
                .toJobParameters();

        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if(lastJobExecution != null){
            for (StepExecution execution : lastJobExecution.getStepExecutions()) {
                BatchStatus batchStatus = execution.getStatus();
                ExitStatus exitStatus = execution.getExitStatus();
                String stepName = execution.getStepName();

                System.out.println("batchStatus = " + batchStatus);
                System.out.println("exitStatus = " + exitStatus);
                System.out.println("stepName = " + stepName);
            }
        }
    }
}
