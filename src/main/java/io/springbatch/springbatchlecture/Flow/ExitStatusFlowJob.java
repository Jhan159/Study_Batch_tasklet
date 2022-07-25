package io.springbatch.springbatchlecture.Flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ExitStatusFlowJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job exitStatus_Job(){
        return this.jobBuilderFactory.get("exitStatus_Job")
                .start(exitStatus_Step1())
                    .on("FAILED")
                    .to(exitStatus_Step2())
                    .on("PASS")
                    .stop()
                .end()
                .build();
    }

    private Step exitStatus_Step1() {
        return this.stepBuilderFactory.get("exitStatus_Step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("exitStatus_Step1 setExitStatus FAILED");
                    stepContribution.getStepExecution().setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    private Step exitStatus_Step2() {
        return this.stepBuilderFactory.get("exitStatus_Step2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("exitStatus_Step2 executed");
                    return RepeatStatus.FINISHED;
                })
                .listener(new ExitCheckListener())
                .build();
    }
}
