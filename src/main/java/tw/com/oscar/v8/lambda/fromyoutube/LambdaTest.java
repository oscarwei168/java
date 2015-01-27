package tw.com.oscar.v8.lambda.fromyoutube;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @see http://www.agiledeveloper.com/downloads.html
 */
interface Fly {
    default public void takeOff() {
        System.out.println("Fly::takeOff");
    }

    default public void turn() {
        System.out.println("Fly::turn");
    }

    default public void cruise() {
        System.out.println("Fly::cruise");
    }

    default public void land() {
        System.out.println("Fly::land");
    }
}

interface FastFly extends Fly {
    default public void takeOff() {
        System.out.println("FastFly::takeOff");
    }
}

class Vehicle {
    public void land() {
        System.out.println("Vehicle::land");
    }
}

interface Sail {
    default public void cruise() {
        System.out.println("Sail:cruise");
    }
}

class SeaPlane extends Vehicle implements FastFly, Sail {
    @Override
    public void cruise() {
        System.out.println("SeaPlane::cruise");
        FastFly.super.cruise();
    }
}

/**
 * Created by oscarwei on 2015/1/10.
 */
public class LambdaTest {

    public static void main(String[] args) {

        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> numbers = Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9, 10);

        // Example 1
        for (int i = 0; i < values.size(); i++) {
            System.out.println(values.get(i));
        }

        // Example 2 - external iterator
        for (int value : values) {
            System.out.println(value);
        }

        // Example 3 - internal iterator, but generate anonymous inner class(LambdaTest$1.class)
        values.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer value) {
                System.out.println(value);
            }
        });

        // Example 4 - no more anonymous inner class
        values.forEach((Integer value) -> System.out.println(value));
        values.forEach((value) -> System.out.println(value));
        values.forEach(value -> System.out.println(value));
        values.forEach(System.out::println);

        // Example 5
        int total = 0;
        for (int e : values) {
            total += e * 2;
        }
        System.out.println(total);

        // Example 6 - Stream API
        System.out.println(values.stream().map(e -> e * 2).reduce(0, (t, e) -> t + e));

        // Example 7 - default method
        // default method have '4 rules' need to note
        new LambdaTest().use();

        // Example 8 - imperative-style programming
        System.out.println(totalValues(values));
        System.out.println(totalEvenValues(values));
        System.out.println(totalOddValues(values));

        // Example 9 - functional-style programming
        System.out.println(totalValues(values, e -> false));
        System.out.println(totalValues(values, e -> true));
        System.out.println(totalValues(values, e -> e % 2 == 0));
        System.out.println(totalValues(values, e -> e % 2 != 0));

        // Example 10
        // the double of the first even number in the list
        System.out.println("Example 10");
        int result = 0;
        for (int e : numbers) {
            if (e > 3 && e % 2 == 0) {
                result = e * 2;
                break;
            }
        }
        System.out.println(result);
        System.out.println(numbers.stream().filter(e -> e > 3).filter(e -> e % 2 == 0).map(e -> e
                * 2).findFirst().orElse(0));
        // egar
        System.out.println(numbers.stream().filter(LambdaTest::isGT3).filter(LambdaTest::isEven)
                .map(LambdaTest::doubleIt).findFirst());
        // lazy
        System.out.println("Running...");
        numbers.stream().filter(LambdaTest::isGT3).filter(LambdaTest::isEven).map(LambdaTest::doubleIt);
        System.out.println("Done");
    }

    private void use() {
        SeaPlane seaPlane = new SeaPlane();
        seaPlane.takeOff();
        seaPlane.turn();
        seaPlane.cruise();
        seaPlane.land();
    }

    private static int totalValues(List<Integer> numbers) {
        int total = 0;
        for (int e : numbers) {
            total += e;
        }
        return total;
    }

    private static int totalEvenValues(List<Integer> numbers) {
        int total = 0;
        for (int e : numbers) {
            if (e % 2 == 0) total += e;
        }
        return total;
    }

    private static int totalOddValues(List<Integer> numbers) {
        int total = 0;
        for (int e : numbers) {
            if (e % 2 != 0) total += e;
        }
        return total;
    }


    private static int totalValues(List<Integer> numbers, Predicate<Integer> selector) {
//        int total = 0;
//        for (int e : numbers) {
//            if (selector.test(e)) total += e;
//        }
//        return total;
        return numbers.stream().filter(selector).reduce(0, (t, e) -> t + e);
    }

    public static boolean isGT3(int number) {
        System.out.println("isGT3 for " + number);
        return number > 3;
    }

    private static boolean isEven(int number) {
        System.out.println("isEven for " + number);
        return number % 2 == 0;
    }

    private static int doubleIt(int number) {
        System.out.println("doubleIt for " + number);
        return number * 2;
    }
}
