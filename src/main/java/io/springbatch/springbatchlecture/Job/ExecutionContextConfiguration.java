package io.springbatch.springbatchlecture.Job;

import io.springbatch.springbatchlecture.ExecutionTasklet.Tasklet1;
import io.springbatch.springbatchlecture.ExecutionTasklet.Tasklet2;
import io.springbatch.springbatchlecture.ExecutionTasklet.Tasklet3;
import io.springbatch.springbatchlecture.ExecutionTasklet.Tasklet4;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Tasklet1 tasklet1;
    private final Tasklet2 tasklet2;
    private final Tasklet3 tasklet3;
    private final Tasklet4 tasklet4;

    @Bean
    public Job jobExecution(){
        return jobBuilderFactory.get("jobExecution")
                .start(jobExecution_step1())
                .next(jobExecution_step2())
                .next(jobExecution_step3())
                .next(jobExecution_step4())
                .build();
    }

    @Bean
    public Step jobExecution_step1() {
        return stepBuilderFactory.get("jobExecution_step1")
                .tasklet(tasklet1)
                .build();
    }

    @Bean
    public Step jobExecution_step2() {
        return stepBuilderFactory.get("jobExecution_step2")
                .tasklet(tasklet2)
                .build();
    }

    @Bean
    public Step jobExecution_step3() {
        return stepBuilderFactory.get("jobExecution_step3")
                .tasklet(tasklet3)
                .build();
    }

    @Bean
    public Step jobExecution_step4() {
        return stepBuilderFactory.get("jobExecution_step4")
                .tasklet(tasklet4)
                .build();
    }
}
