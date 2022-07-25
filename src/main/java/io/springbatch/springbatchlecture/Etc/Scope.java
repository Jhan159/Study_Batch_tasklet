package io.springbatch.springbatchlecture.Etc;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class Scope {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job scopeJob() {
        return jobBuilderFactory.get("scopeJob")
                .listener(new scopeJobListener())
                .start(scopeStep1(null))
                .next(scopeStep2())
                .build();
    }

    private class scopeJobListener implements JobExecutionListener {
        @Override
        public void beforeJob(JobExecution jobExecution) {
            jobExecution.getExecutionContext().putString("name", "user1");
        }

        @Override
        public void afterJob(JobExecution jobExecution) {

        }
    }

    @Bean
    @JobScope
    public Step scopeStep1(@Value("#{jobParameters['message']}") String message) {
        System.out.println("message = " + message);
        return stepBuilderFactory.get("scopeStep1")
                .tasklet(scopeTasklet1(null))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet scopeTasklet1(@Value("#{jobExecutionContext['name']}") String name) {
        System.out.println("scopeTasklet1 name = " + name);
        return (stepContribution, chunkContext) -> {
            System.out.println(">>> scopeStep1 executed");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step scopeStep2() {
        return stepBuilderFactory.get("scopeStep2")
                .listener(new scopeStepListener())
                .tasklet(scopeTasklet2(null))
                .build();
    }

    private class scopeStepListener implements StepExecutionListener {
        @Override
        public void beforeStep(StepExecution stepExecution) {
            stepExecution.getExecutionContext().putString("name2", "user2");
        }

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            return null;
        }
    }

    @Bean
    @StepScope
    public Tasklet scopeTasklet2(@Value("#{stepExecutionContext['name2']}") String name2) {
        System.out.println("scopeTasklet2 name = " + name2);
        return (stepContribution, chunkContext) -> {
            System.out.println(">>> scopeStep2 executed");
            return RepeatStatus.FINISHED;
        };
    }
}
