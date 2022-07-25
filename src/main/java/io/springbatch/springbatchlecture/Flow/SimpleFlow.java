package io.springbatch.springbatchlecture.Flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SimpleFlow {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleFlow_Job(){
        return jobBuilderFactory.get("simpleFlow_Job")
                .start(simpleFlow_Flow1())
                    .on("COMPLETED")
                    .to(simpleFlow_Flow2())
                .from(simpleFlow_Flow1())
                    .on("FAILED")
                    .to(simpleFlow_Flow3())
                .end()
                .build();
    }

    @Bean
    public Flow simpleFlow_Flow1() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("simpleFlow_Flow1");
        return flowBuilder
                .start(simpleFlow_Step1())
                .next(simpleFlow_Step2())
                .end();
    }

    @Bean
    public Flow simpleFlow_Flow2() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("simpleFlow_Flow2");
        return flowBuilder
                .start(simpleFlow_Flow3())
                .next(simpleFlow_Step5())
                .next(simpleFlow_Step6())
                .end();
    }

    @Bean
    public Flow simpleFlow_Flow3() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("simpleFlow_Flow3");
        return flowBuilder
                .start(simpleFlow_Step3())
                .next(simpleFlow_Step4())
                .end();
    }

    @Bean
    public Step simpleFlow_Step1() {
        return stepBuilderFactory.get("simpleFlow_Step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(">>> simpleFlow Step1 Executed");
                    throw new RuntimeException("step1 Error");
//                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step simpleFlow_Step2() {
        return stepBuilderFactory.get("simpleFlow_Step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(">>> simpleFlow Step2 Executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step simpleFlow_Step3() {
        return stepBuilderFactory.get("simpleFlow_Step3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(">>> simpleFlow Step3 Executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step simpleFlow_Step4() {
        return stepBuilderFactory.get("simpleFlow_Step4")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(">>> simpleFlow Step4 Executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step simpleFlow_Step5() {
        return stepBuilderFactory.get("simpleFlow_Step5")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(">>> simpleFlow Step5 Executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step simpleFlow_Step6() {
        return stepBuilderFactory.get("simpleFlow_Step6")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(">>> simpleFlow Step6 Executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
