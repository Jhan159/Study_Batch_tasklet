package io.springbatch.springbatchlecture.Job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob(){
        return jobBuilderFactory.get("simpleJob")
                .start(simpleJob_step1())
                .next(simpleJob_step2())
                .next(simpleJob_step3())
//                .incrementer(new RunIdIncrementer())
                .incrementer(new CustomJobParamIncrementer())
//                .validator(new CustomJobParamValidator())
                .validator(new DefaultJobParametersValidator(new String[]{"name"}, new String[]{}))
                .preventRestart()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {

                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {

                    }
                })
                .build();
    }

    @Bean
    public Step simpleJob_step1(){
        return stepBuilderFactory.get("simpleJob_step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("simpleJob_step1 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step simpleJob_step2(){
        return stepBuilderFactory.get("simpleJob_step2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("simpleJob_step2 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }

    @Bean
    public Step simpleJob_step3(){
        return stepBuilderFactory.get("simpleJob_step3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        //강제 실패 설정
//                        chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
//                        stepContribution.setExitStatus(ExitStatus.STOPPED);
                        //강제 실패 설정
                        System.out.println("simpleJob_step3 execute");
                        return RepeatStatus.FINISHED;
                    }
                })
                .build();
    }
}
