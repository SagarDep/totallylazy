package com.googlecode.totallylazy;

import com.googlecode.totallylazy.callables.AscendingComparator;
import com.googlecode.totallylazy.callables.DescendingComparator;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static com.googlecode.totallylazy.Pair.pair;

public final class Callables {
    public static <T> Callable1<? super T, T> nullGuard(final Callable1<? super T, T> callable) {
        return new Callable1<T, T>() {
            public T call(T o) throws Exception {
                if(o == null) return null;
                return callable.call(o);
            }
        };
    }

    public static <T> Callable1<Sequence<T>, Sequence<T>> reduceAndShift(final Callable2<T, T, T> action) {
        return new Callable1<Sequence<T>, Sequence<T>>() {
            public final Sequence<T> call(final Sequence<T> values) throws Exception {
                return values.tail().add(values.reduceLeft(action));
            }
        };
    }

    public static <T, S> Callable1<T, S> cast(final Class<S> aClass) {
        return new Callable1<T, S>() {
            public final S call(final T t) throws Exception {
                return (S) t;
            }
        };
    }

    public static Callable1<Object, Class> toClass() {
        return new Callable1<Object, Class>() {
            public final Class call(final Object o) throws Exception {
                return o.getClass();
            }
        };
    }

    public static <T> Comparator<T> ascending(final Callable1<T, ? extends Comparable> callable) {
        return new AscendingComparator<T>(callable);
    }

    public static <T> Comparator<T> descending(final Callable1<T, ? extends Comparable> callable) {
        return new DescendingComparator<T>(callable);
    }

    public static <T> Callable1<T, Integer> length() {
        return new Callable1<T, Integer>() {
            public final Integer call(final Object object) throws Exception {
                Class aClass = object.getClass();
                if (aClass.isArray()) {
                    return Array.getLength(object);
                }
                throw new UnsupportedOperationException("Dont support methods or fields yet");
            }
        };
    }

    public static <T> Callable1<Collection<? extends T>, ? extends Comparable> size() {
        return new Callable1<Collection<? extends T>, Comparable>() {
            public Comparable call(Collection<? extends T> set) throws Exception {
                return set.size();
            }
        };
    }

    public static <T> Callable1<Sequence<T>, Sequence<T>> realise() {
        return new Callable1<Sequence<T>, Sequence<T>>() {
            public final Sequence<T> call(final Sequence<T> sequence) throws Exception {
                return sequence.realise();
            }
        };
    }

    public static <T> Callable1<Future<T>, T> realiseFuture() {
        return new Callable1<Future<T>, T>() {
            public final T call(final Future<T> future) throws Exception {
                return future.get();
            }
        };
    }

    public static <T> Callable1<? super First<T>, T> first(Class<T> aClass) {
        return first();
    }

    public static <T> Callable1<? super First<T>, T> first() {
        return new Callable1<First<T>, T>() {
            public final T call(final First<T> first) throws Exception {
                return first.first();
            }
        };
    }

    public static <T> Callable1<? super Second<T>, T> second(Class<T> aClass) {
        return second();
    }

    public static <T> Callable1<? super Second<T>, T> second() {
        return new Callable1<Second<T>, T>() {
            public final T call(final Second<T> second) throws Exception {
                return second.second();
            }
        };
    }

    public static <T> Callable1<? super Iterable<T>, T> head() {
        return new Callable1<Iterable<T>, T>() {
            public final T call(final Iterable<T> iterable) throws Exception {
                return Sequences.head(iterable);
            }
        };
    }

    public static <T> Callable1<T, String> asString() {
        return new Callable1<T, String>() {
            public final String call(final T value) {
                return value.toString();
            }
        };
    }

    public static Callable2<Integer, Object, Integer> asHashCode() {
        return new Callable2<Integer, Object, Integer>() {
            public Integer call(Integer hash, Object value) throws Exception {
                return hash * (value == null ? 0 :value.hashCode());
            }
        };
    }


    public static <T>  Callable1<? super Iterable<? extends T>, Iterator<? extends T>> asIterator() {
        return new Callable1<Iterable<? extends T>, Iterator<? extends T>>() {
            public Iterator<? extends T> call(Iterable<? extends T> iterable) throws Exception {
                return iterable.iterator();
            }
        };
    }

    public static <T> Callable1<? super Iterator<? extends T>, Iterable<? extends T>> asIterable() {
        return new Callable1<Iterator<? extends T>, Iterable<? extends T>>() {
            public final Iterable<? extends T> call(final Iterator<? extends T> iterator) throws Exception {
                return Sequences.sequence(iterator);
            }
        };
    }

    public static <T> Callable<T> returns(final T t) {
        return new Callable<T>() {
            public final T call() throws Exception {
                return t;
            }
        };
    }

    public static <T> Callable1<T, T> returnArgument() {
        return new Callable1<T, T>() {
            public final T call(final T value) {
                return value;
            }
        };
    }

    public static <T> Callable1<T, T> returnArgument(final Class<T> aClass) {
        return returnArgument();
    }

    public static <T> Callable<T> aNull(final Class<T> aClass) {
        return new Callable<T>() {
            public final T call() throws Exception {
                return null;
            }
        };
    }

    public static <T> Callable<T> callThrows(final Exception e) {
        return new Callable<T>() {
            public final T call() throws Exception {
                throw e;
            }
        };
    }

    public static <T> Callable<T> callThrows(final Exception e, final Class<T> aClass) {
        return callThrows(e);
    }

    public static <T> Callable1<Callable<T>, T> call() {
        return new Callable1<Callable<T>, T>() {
            public final T call(final Callable<T> callable) throws Exception {
                return callable.call();
            }
        };
    }

    public static <T> Callable1<Callable<T>, T> call(final Class<T> aClass) {
        return call();
    }

    public static <T,R,S> Callable1<R, S> curry(final Callable2<T, R, S> callable, final T value) {
        return Callers.call(curry(callable), value);
    }

    public static <T,R,S> Callable1<T, Callable1<R, S>> curry(final Callable2<T, R, S> callable) {
        return new Callable1<T, Callable1<R, S>>() {
            public final Callable1<R, S> call(final T t) throws Exception {
                return new Callable1<R, S>() {
                    public final S call(final R r) throws Exception {
                        return callable.call(t, r);
                    }
                };
            }
        };
    }

    public static <T,R> Callable<R> curry(final Callable1<T, R> callable, final T value) {
        return new Callable<R>() {
            public final R call() throws Exception {
               return callable.call(value);
            }
        };
    }

    public static <T, R, S> Callable2<T, R, S> unCurry(final Callable1<T, Callable1<R,S>> callable) {
        return new Callable2<T, R, S>() {
            public final S call(final T t, final R r) throws Exception {
                return callable.call(t).call(r);
            }
        };
    }

    public static <L> Callable1<Either<L, ?>, L> left(Class<L> aClass) {
        return left();
    }

    public static <L> Callable1<Either<L, ?>, L> left() {
        return new Callable1<Either<L, ?>, L>() {
            public L call(Either<L, ?> either) throws Exception {
                return either.left();
            }
        };
    }

    public static <R> Callable1<Either<?, R>, R> right(Class<R> aClass) {
        return right();
    }

    public static <R> Callable1<Either<?, R>, R> right() {
        return new Callable1<Either<?, R>, R>() {
            public R call(Either<?, R> either) throws Exception {
                return either.right();
            }
        };
    }

}