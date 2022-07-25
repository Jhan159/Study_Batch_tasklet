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
public class FlowStep {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job flowStep_Job(){
        return jobBuilderFactory.get("flowStep_Job")
                .start(flowStep_flowStep())
                .next(flowStep_Step2())
                .build();
    }

    private Step flowStep_flowStep() {
        return stepBuilderFactory.get("flowStep_flowStep")
                .flow(flowStep_flow())
                .build();
    }

    private Flow flowStep_flow() {

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flowStep_flow");
        return flowBuilder.start(flowStep_Step1())
                .end();
    }

    @Bean
    public Step flowStep_Step1() {
        return stepBuilderFactory.get("flowStep_Step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(">>> flowStep_Step1 executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step flowStep_Step2() {
        return stepBuilderFactory.get("flowStep_Step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println(">>> flowStep_Step2 executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
