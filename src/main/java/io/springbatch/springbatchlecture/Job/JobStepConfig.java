package io.springbatch.springbatchlecture.Job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.batch.core.step.job.JobParametersExtractor;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobStepConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job JobStepConfig_job(){
        return jobBuilderFactory.get("JobStepConfig_job")
                .start(jobStep(null))   //jobStep
                .next(jobStepConfig_step2())
                .build();
    }

    @Bean
    public Step jobStep(JobLauncher jobLauncher){
        return stepBuilderFactory.get("jobStep")
                .job(JobStepConfig_childJob())
                .launcher(jobLauncher)
                .parametersExtractor(jobParamegersExtractor())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        stepExecution.getExecutionContext().putString("name", "user1");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        return null;
                    }
                })
                .build();
    }

    private DefaultJobParametersExtractor jobParamegersExtractor() {
        DefaultJobParametersExtractor defaultExtractor = new DefaultJobParametersExtractor();
        defaultExtractor.setKeys(new String[]{"name"});
        return defaultExtractor;
    }

    @Bean
    public Job JobStepConfig_childJob() {
        return jobBuilderFactory.get("JobStepConfig_childJob")
                .start(jobStepConfig_step1())
                .build();
    }

    @Bean
    public Step jobStepConfig_step1(){
        return stepBuilderFactory.get("jobStepConfig_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("jobStepConfig_step1 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step jobStepConfig_step2(){
        return stepBuilderFactory.get("jobStepConfig_step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("jobStepConfig_step2 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
