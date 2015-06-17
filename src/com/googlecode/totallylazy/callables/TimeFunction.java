package com.googlecode.totallylazy.callables;

import com.googlecode.totallylazy.Callable1;
import com.googlecode.totallylazy.Function;

import static com.googlecode.totallylazy.callables.TimeCallable.calculateMilliseconds;

public final class TimeFunction<A,B> extends Function<A, B> {
    private final Callable1<? super A, ? extends B> callable;
    private final Callable1<? super Number, ?> reporter;

    private TimeFunction(Callable1<? super A, ? extends B> callable, Callable1<? super Number, ?> reporter) {
        this.callable = callable;
        this.reporter = reporter;
    }

    @Override
    public final B call(A a) throws Exception {
        long start = System.nanoTime();
        B result = callable.call(a);
        reporter.call(calculateMilliseconds(start, System.nanoTime()));
        return result;
    }

    public static <A,B> TimeFunction<A,B> time1(Callable1<? super A, ? extends B> callable){
        return time1(callable, TimeCallable.DEFAULT_REPORTER);
    }

    public static <A,B> TimeFunction<A,B> time1(Callable1<? super A, ? extends B> callable, Callable1<? super Number, ?> reporter){
        return new TimeFunction<A,B>(callable, reporter);
    }
}
