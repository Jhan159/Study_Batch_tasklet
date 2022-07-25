package io.springbatch.springbatchlecture.Flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowJobFlow {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowJobFlow_Job(){
        return jobBuilderFactory.get("flowJobFlow_Job")
                .start(flowJobflow_step1())
                    .on("FAILED")
                    .to(flowJobflow_step2())
                    .on("FAILED")
                    .stop()
                .from(flowJobflow_step1())
                    .on("*")
                    .to(flowJobflow_step3())
                    .next(flowJobflow_step4())
                .from(flowJobflow_step2())
                    .on("*")
                    .to(flowJobflow_step5())
                    .end()
                .build();
    }
    @Bean
    public Step flowJobflow_step1() {
        return stepBuilderFactory.get("flowJobflow_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowJobflow_step1 executed");
//                        throw new RuntimeException("flowJobflow_step1 FAILED");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowJobflow_step2() {
        return stepBuilderFactory.get("flowJobflow_step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowJobflow_step2 executed");
//                        throw new RuntimeException("flowJobflow_step1 FAILED");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowJobflow_step3() {
        return stepBuilderFactory.get("flowJobflow_step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowJobflow_step3 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowJobflow_step4() {
        return stepBuilderFactory.get("flowJobflow_step4")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowJobflow_step4 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowJobflow_step5() {
        return stepBuilderFactory.get("flowJobflow_step5")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowJobflow_step5 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
