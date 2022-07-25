package io.springbatch.springbatchlecture.Step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StepBuilderConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepBuildConfig_job(){
        return jobBuilderFactory.get("stepBuildConfig_job")
                .incrementer(new RunIdIncrementer())
                .start(stepBuildConfig_step1())
                .next(stepBuildConfig_step2())
                .next(stepBuildConfig_step3())
                .build();
    }

    @Bean
    public Step stepBuildConfig_step1() {
        return stepBuilderFactory.get("stepBuildConfig_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("stepBuildConfig_step1 executed");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step stepBuildConfig_step2() {
        return stepBuilderFactory.get("stepBuildConfig_step2")
                .<String, String>chunk(3)
                .reader(new ItemReader<String>() {
                    @Override
                    public String read() throws Exception {
                        System.out.println("stepBuildConfig_step2 read");
                        return null;
                    }
                })
                .processor(new ItemProcessor<String, String>() {
                    @Override
                    public String process(String s) throws Exception {
                        System.out.println("stepBuildConfig_step2 process");
                        return null;
                    }
                })
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> list) throws Exception {
                        System.out.println("stepBuildConfig_step2 write");
                    }
                })
                .build();
    }

    @Bean
    public Step stepBuildConfig_step3() {
        return stepBuilderFactory.get("stepBuildConfig_step3")
                .job(stepBuildConfig_job2())
                .build();
    }

    @Bean
    public Step stepBuildConfig_step4() {
        return stepBuilderFactory.get("stepBuildConfig_step4")
                .flow(stepBuildConfig_flow())
                .build();
    }

    @Bean
    public Step stepBuildConfig_step5() {
        return stepBuilderFactory.get("stepBuildConfig_step5")
                .partitioner(stepBuildConfig_step2())
                .gridSize(2)
                .build();
    }

    @Bean
    public Job stepBuildConfig_job2(){
        return jobBuilderFactory.get("stepBuildConfig_job2")
                .incrementer(new RunIdIncrementer())
                .start(stepBuildConfig_step4())
//                .next(stepBuildConfig_step5())
                .build();
    }

    @Bean
    public Flow stepBuildConfig_flow(){
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("stepBuildConfig_flow");
        return flowBuilder
                .start(stepBuildConfig_step2())
                .end();
    }

}
