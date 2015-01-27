package tw.com.oscar.v7.concurrency.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author oscarwei
 * @since 2015/1/23
 */
public class ForkJoinSample {

    public static final long THRESHOLD = 10_000;

    public static void main(String[] args) {
        ForkJoinTask<Long> task = new DoSomethingTask();
        new ForkJoinPool().invoke(task);
    }

    public static class DoSomethingTask extends java.util.concurrent.RecursiveTask<Long> {

        @Override
        protected Long compute() {
            // Return type is dependent on the type parameter
            DoSomethingTask leftTask = new DoSomethingTask();
            leftTask.fork();
            DoSomethingTask rightTask = new DoSomethingTask();
            Long rightResult = rightTask.compute();
            Long leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }
}
