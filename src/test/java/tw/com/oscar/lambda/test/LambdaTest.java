package tw.com.oscar.lambda.test;

import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * @author oscarwei
 * @since 2015/1/16
 */
public class LambdaTest {

    @Test
    public void integerOperation() {
        int sum = Integer.sum(10, 10);
        assertTrue(20 == sum);

        int max = Integer.max(1, 2);
        assertTrue(2 == max);

        int min = Integer.min(1, 2);
        assertTrue(1 == min);
    }

    @Test
    public void allMember() {
        List<Integer> list = Stream.of(asList(1, 2), asList(3, 4)).flatMap(numbers -> numbers
                .stream()).collect(toList());
        assertEquals(asList(1, 2, 3, 4), list);
    }


}
