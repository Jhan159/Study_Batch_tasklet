package io.springbatch.springbatchlecture.Step;

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
public class StepConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepConfig_job(){
        return jobBuilderFactory.get("stepConfig_job")
                .start(StepConfig_step1())
                .next(StepConfig_step2())
                .build();
    }

    @Bean
    public Step StepConfig_step1() {
        return stepBuilderFactory.get("StepConfig_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("StepConfig_step1 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step StepConfig_step2() {
        return stepBuilderFactory.get("StepConfig_step2")
                .tasklet(new CustomTasklet())
                .build();
    }

    public static class CustomTasklet implements Tasklet{

        @Override
        public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
            System.out.println("TaskletClass executed");
            return RepeatStatus.FINISHED;
        }
    }
}
