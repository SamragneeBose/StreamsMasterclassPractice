import org.junit.Test;

import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

import static java.util.function.IntUnaryOperator.identity;
import static org.assertj.core.api.Assertions.assertThat;

public class Test04_FunctionCombination {

    /**
     * Suppose you have a shopping website
     * where the customer can apply a filter
     * to limit the products shown.
     * -
     * Suppose you want the customer to be able
     * to apply two filters to the product list.
     * -
     * Now, how about three filters?
     * -
     * Two Predicates can be combined using the
     * Predicate.and() method.
     * -
     * This is all we need to write a method
     * that combines an arbitrary number of predicates.
     */
    @Test
    public void functionCombination_1()
    {
        List<Predicate<String>> predicates =
                List.of(s -> s != null, s -> !s.isEmpty(), s -> s.length() < 5);

        // TODO
        Predicate<String> combinedPredicate =
                predicates.stream()
                .reduce(s->true, Predicate::and); // (p1, p2) -> p1.and(p2)

        assertThat(combinedPredicate.test("")).isFalse();
        assertThat(combinedPredicate.test(null)).isFalse();
        assertThat(combinedPredicate.test("Hello")).isFalse();
        assertThat(combinedPredicate.test("Java")).isTrue();
    }


    /**
     * An IntUnaryOperator is a functional interface
     * that takes an int and returns an int.
     * -
     * Write a method that combines an arbitrary sized
     * list of IntUnaryOperators into a single one
     * -
     * Use streams and IntUnaryOperator.andThen() method.
     * -
     * Use your method to combine functions that
     * add one, multiply by two, and add three.
     */
    @Test
    public void functionCombination_2()
    {

        List<IntUnaryOperator> operators =
                List.of(i -> i + 1, i -> i * 2, i -> i + 3);

        // TODO
        IntUnaryOperator combinedOperator=
                operators.stream()
                .reduce(identity(), IntUnaryOperator::andThen); // i -> i, (op1, op2) -> op1.andThen(op2)

        assertThat(combinedOperator.applyAsInt(5)).isEqualTo(15);
    }
}
