package io.springbatch.springbatchlecture.ExecutionTasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class Tasklet4 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        Object name = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name");
        System.out.println("name = " + name);

        System.out.println("step4 executed");
        return RepeatStatus.FINISHED;
    }
}
