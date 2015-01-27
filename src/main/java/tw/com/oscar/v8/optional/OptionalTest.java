package tw.com.oscar.v8.optional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author oscarwei
 * @since 2015/1/11
 */
public class OptionalTest {

    public static void main(String[] args) throws Exception {

        Optional empty = Optional.empty();
        System.out.println(empty.orElse("Null"));
        System.out.println(empty.orElseGet(() -> "functional-style programming...")); // lazy

        Optional<String> hasSomething = Optional.of("Oscar");
        System.out.println(hasSomething.get());
        hasSomething.orElseThrow(Exception::new);
        System.out.println(hasSomething.isPresent());
        hasSomething.ifPresent(e -> System.out.println("" + e));
        hasSomething.filter(e -> e.contains("o")).ifPresent(e -> System.out.println("" + e));
        hasSomething.map(String::trim).filter(e -> e.length() > 1).ifPresent(e -> System.out
                .println("" + e));
        hasSomething.flatMap(OptionalTest::test);
        hasSomething.map(String::trim).orElse("");
        Optional<String> nullable = Optional.<String>ofNullable("");
        List<String> toList = nullable.map(Collections::singletonList).orElse(Collections.emptyList());
        System.out.println(nullable);
    }

    private static Optional<String> test(String msg) {
        return Optional.ofNullable(msg);
    }

}
