package com.googlecode.totallylazy.numbers;

import com.googlecode.totallylazy.Segment;
import com.googlecode.totallylazy.Sequence;
import com.googlecode.totallylazy.matchers.NumberMatcher;
import org.junit.Test;

import static com.googlecode.totallylazy.Callables.asString;
import static com.googlecode.totallylazy.Predicates.and;
import static com.googlecode.totallylazy.Predicates.or;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.Strings.isPalindrome;
import static com.googlecode.totallylazy.matchers.NumberMatcher.is;
import static com.googlecode.totallylazy.numbers.Numbers.even;
import static com.googlecode.totallylazy.numbers.Numbers.fibonacci;
import static com.googlecode.totallylazy.numbers.Numbers.isZero;
import static com.googlecode.totallylazy.numbers.Numbers.lcm;
import static com.googlecode.totallylazy.numbers.Numbers.lessThanOrEqualTo;
import static com.googlecode.totallylazy.numbers.Numbers.maximum;
import static com.googlecode.totallylazy.numbers.Numbers.mod;
import static com.googlecode.totallylazy.numbers.Numbers.multiply;
import static com.googlecode.totallylazy.numbers.Numbers.primeFactorsOf;
import static com.googlecode.totallylazy.numbers.Numbers.range;
import static com.googlecode.totallylazy.numbers.Numbers.sum;
import static com.googlecode.totallylazy.predicates.WherePredicate.where;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProjectEuler {
    @Test
    public void problem1() throws Exception {
        assertThat(range(1, 999).filter(where(mod(3), isZero()).or(where(mod(5), isZero()))).reduce(sum()), is(233168));
        assertThat(range(1, 999).filter(or(sequence(3, 5).map(where(mod(), isZero())))).reduce(sum()), is(233168));
    }

    @Test
    public void problem2() throws Exception {
        assertThat(fibonacci().takeWhile(lessThanOrEqualTo(4000000)).filter(even()).reduce(sum()), is(4613732));
    }

    @Test
    public void problem3() throws Exception {
        assertThat(primeFactorsOf(600851475143L).last(), is(6857));
    }

    @Test
    public void problem4() throws Exception {
        assertThat(range(999, 100).cartesianProduct().map(multiply().pair()).
                filter(where(asString(), isPalindrome())).reduce(maximum()), is(906609));
    }

    @Test
    public void problem5() throws Exception {
        assertThat(range(1, 20).reduce(lcm()), is(232792560));
    }
}
