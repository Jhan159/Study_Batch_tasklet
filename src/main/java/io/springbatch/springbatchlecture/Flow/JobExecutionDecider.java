package io.springbatch.springbatchlecture.Flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobExecutionDecider {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job decider_Job(){
        return jobBuilderFactory.get("decider_Job")
                .start(decider_Step())
                .next(decider())
                .from(decider()).on("ODD").to(decider_oddStep())
                .from(decider()).on("EVEN").to(decider_evenStep())
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public org.springframework.batch.core.job.flow.JobExecutionDecider decider(){
        return new CustomDecider();
    }

    private class CustomDecider implements org.springframework.batch.core.job.flow.JobExecutionDecider {

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

            JobParameters jobParameters = jobExecution.getJobParameters();
            int count = Integer.parseInt(jobParameters.getString("count"));
            String result = "ODD";
            if (count % 2 == 0) {
                result = "EVEN";
            }

            return new FlowExecutionStatus(result);
        }
    }

    @Bean
    public Step decider_Step() {
        return stepBuilderFactory.get("decider_Step")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("decider_Step executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step decider_evenStep() {
        return stepBuilderFactory.get("decider_evenStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("decider_evenStep executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step decider_oddStep() {
        return stepBuilderFactory.get("decider_oddStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("decider_oddStep executed");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


}
