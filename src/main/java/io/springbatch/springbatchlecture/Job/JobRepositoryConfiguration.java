package io.springbatch.springbatchlecture.Job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
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
public class JobRepositoryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobExecutionListener jobExecutionListener;

    @Bean
    public Job jobRepositoryConfig(){
        return jobBuilderFactory.get("jobRepositoryConfig")
                .start(jobRepositoryConfig_step1())
                .next(jobRepositoryConfig_step2())
                .listener(jobExecutionListener)
                .build();
    }

    @Bean
    public Step jobRepositoryConfig_step1() {
        return stepBuilderFactory.get("jobRepositoryConfig_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step1 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step jobRepositoryConfig_step2() {
        return stepBuilderFactory.get("jobRepositoryConfig_step2")
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
