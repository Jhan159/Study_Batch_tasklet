package io.springbatch.springbatchlecture.Job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CustomTaskletConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job customTasklet_job(){
        return jobBuilderFactory.get("customTasklet_job")
                .start(customTasklet_Step1())
                .build();
    }

    @Bean
    public Step customTasklet_Step1(){
        // TaskStep
        return stepBuilderFactory.get("customTasklet_Step1")
                .tasklet(new CustomTasklet())
                .startLimit(3)
                .allowStartIfComplete(true)
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        System.out.println("customTaskletStep Start");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        System.out.println("customTaskletStep End");
                        return null;
                    }
                })
                .build();
    }
}
