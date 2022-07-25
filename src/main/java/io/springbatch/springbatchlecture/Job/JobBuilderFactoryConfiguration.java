package io.springbatch.springbatchlecture.Job;

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
public class JobBuilderFactoryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    //simpleJobBuilder
    @Bean
    public Job simplejobBuilderConfig(){
        return jobBuilderFactory.get("simplejobBuilderConfig")
                .start(simplejobBuilderConfig_step1())
                .next(simplejobBuilderConfig_step2())
                .build();
    }

    @Bean
    public Step simplejobBuilderConfig_step1(){
        return stepBuilderFactory.get("simplejobBuilderConfig_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("simplejobBuilderConfig_step1 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step simplejobBuilderConfig_step2(){
        return stepBuilderFactory.get("simplejobBuilderConfig_step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("simplejobBuilderConfig_step2 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //flowJobBuilder

    @Bean
    public Job flowjobBuilderConfig(){
        return jobBuilderFactory.get("flowjobBuilderConfig")
                .start(flowjobBuilderConfig_flow())
                .next(flowjobBuilderConfig_step3())
                .end()
                .build();
    }

    @Bean
    public Flow flowjobBuilderConfig_flow(){
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flowjobBuilderConfig_flow");
        Flow flow = flowBuilder
                .start(flowjobBuilderConfig_step1())
                .next(flowjobBuilderConfig_step2())
                .build();

        return flow;
    }

    @Bean
    public Step flowjobBuilderConfig_step1(){
        return stepBuilderFactory.get("flowjobBuilderConfig_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowjobBuilderConfig_step1 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowjobBuilderConfig_step2(){
        return stepBuilderFactory.get("flowjobBuilderConfig_step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowjobBuilderConfig_step2 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step flowjobBuilderConfig_step3(){
        return stepBuilderFactory.get("flowjobBuilderConfig_step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("flowjobBuilderConfig_step3 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
