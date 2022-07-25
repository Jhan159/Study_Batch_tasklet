package io.springbatch.springbatchlecture.Flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flow_flowJob(){
        return jobBuilderFactory.get("flow_flowJob")
                .start(flowJob_step1())
                .on("COMPLETED").to(flowJob_step3())
                .from(flowJob_step1())
                .on("FAILED").to(flowJob_step2())
                .end()
                .build();
    }

    @Bean
    public Step flowJob_step1() {
        return stepBuilderFactory.get("flowJob_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowJob_step1 executed");
                        return RepeatStatus.FINISHED;
//                        throw new RuntimeException("RuntimeExceprion");
                    }
                })
                .build();
    }

    @Bean
    public Step flowJob_step2() {
        return stepBuilderFactory.get("flowJob_step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowJob_step2 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowJob_step3() {
        return stepBuilderFactory.get("flowJob_step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowJob_step3 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
