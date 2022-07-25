package io.springbatch.springbatchlecture.Job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobParameterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobParameterConfig(){
        return jobBuilderFactory.get("jobParameterConfig")
                .start(jobParameterConfig_step1())
                .next(jobParameterConfig_step2())
                .build();
    }

    @Bean
    public Step jobParameterConfig_step1() {
        return stepBuilderFactory.get("jobParameterConfig_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

                        JobParameters jobParameters = stepContribution.getStepExecution().getJobExecution().getJobParameters();
//                        JobParameters jobParameters = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters();
//                        Map<String, Object> jobParametersMap = chunkContext.getStepContext().getJobParameters();
                        jobParameters.getString("name");
                        jobParameters.getLong("seq");
                        jobParameters.getDate("date");
                        jobParameters.getDouble("age");

                        System.out.println("jobParameters = " + jobParameters);
                        System.out.println("step1 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step jobParameterConfig_step2() {
        return stepBuilderFactory.get("jobParameterConfig_step2`")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step2 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
