package me.j360.batchnative;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Package: me.j360.batchnative
 * User: min_xu
 * Date: 16/6/13 下午4:18
 * 说明：
 */
public class WriteTasklet implements Tasklet {

    /** Message */
    private String message;

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
            throws Exception {
        System.out.println(message);
        return RepeatStatus.FINISHED;
    }
}
