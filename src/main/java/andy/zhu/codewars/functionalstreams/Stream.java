package andy.zhu.codewars.functionalstreams;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by AndyZhu on 2017/5/18.
 */
public class Stream<T> {

    // head of the stream
    private T _head;

    // lazy tail of the stream
    private Thunk<Stream<T>> _tail;

    public Stream(T head, Thunk<Stream<T>> tail) {
        this._head = head;
        this._tail = tail;
    }

    // Returns the head of the stream.
    public T headS() {
        return _head;
    }

    // Returns the unevaluated tail of the stream.
    public Thunk<Stream<T>> tailS() {
        return _tail;
    }

    // Returns the evaluated tail of the stream.
    public Stream<T> next() {
        return _tail.get();
    }

    // .------------------------------.
    // | Static constructor functions |
    // '------------------------------'

    // Construct a stream by repeating a value.
    public static <U> Stream<U> repeatS(U x) {
        return new Stream<>(x, Thunk.delay(() -> repeatS(x)));
    }

    // Construct a stream by repeatedly applying a function.
    public static <U> Stream<U> iterateS(Function<U, U> f, U x) {
        return new Stream<>(x, Thunk.delay(() -> iterateS(f, f.apply(x))));
    }

    // Construct a stream by repeating a list forever. (Sadly no pure single linked lists :( )
    public static <U> Stream<U> cycleS(List<U> l) {
        List<U> list = new LinkedList<>(l);
        U head = list.remove(0);
        list.add(head);
        return new Stream<>(head, Thunk.delay(() -> cycleS(list)));
    }

    // Construct a stream by counting numbers starting from a given one.
    public static Stream<Integer> fromS(int x) {
        return new Stream<>(x, Thunk.delay(() -> fromS(x + 1)));
    }

    // Same as @{fromS} but count with a given step width.
    public static Stream<Integer> fromThenS(int x, int d) {
        return new Stream<>(x, Thunk.delay(() -> fromThenS(x + d, d)));
    }

    // .------------------------------------------.
    // | Stream reduction and modification (pure) |
    // '------------------------------------------'

    // Fold a stream from the left.
    public <R> R foldrS(BiFunction<T, Thunk<R>, R> f) {
        return f.apply(headS(), Thunk.delay(() -> next().foldrS(f)));
    }

    // Filter stream with a predicate. (Returns a lazy result.)
    public Thunk<Stream<T>> filterS(Predicate<T> p) {
        return Thunk.delay(() -> p.test(headS())
                ? new Stream<>(headS(), next().filterS(p))
                : next().filterS(p).get());
    }

    // Take a given amount of elements from the stream.
    public LinkedList<T> takeS(int n) {
        LinkedList<T> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            list.add(headS());
            _head = next().headS();
            _tail = next().tailS();
        }
        return list;
    }

    // Drop a given amount of elements from the stream.
    public Stream<T> dropS(int n) {
        Stream<T> next = this;
        for (int i = 0; i < n; i++) {
            next = next.next();
        }
        return next;
    }

    // Combine 2 streams with a function.
    public <U, R> Stream<R> zipWithS(BiFunction<T, U, R> f, Stream<U> other) {
        return new Stream<>(f.apply(headS(), other.headS()), Thunk.delay(() -> next().zipWithS(f, other.next())));
    }

    // Map every value of the stream with a function, returning a new stream.
    public <R> Stream<R> fmap(Function<T, R> f) {
        return new Stream<>(f.apply(headS()), Thunk.delay(() -> next().fmap(f)));
    }

    // Helper class, to create cyclic declarations, may be helpful for generating the fibonacci numbers.
    public static class CyclicRef<T> {
        public T value;
    }

    // Return the stream of all fibonacci numbers.
    public static Stream<Integer> fibS() {
        CyclicRef<Stream<Integer>> fib1 = new CyclicRef<>();
        CyclicRef<Stream<Integer>> fib2 = new CyclicRef<>();
        CyclicRef<Supplier<Stream<Integer>>> fib1Supplier = new CyclicRef<>();
        fib1Supplier.value = () -> {
            fib1.value = new Stream<>(fib1.value.headS() + fib2.value.headS(), Thunk.delay(fib1Supplier.value));
            return fib2.value;
        };
        CyclicRef<Supplier<Stream<Integer>>> fib2Suppiler = new CyclicRef<>();
        fib2Suppiler.value = () -> {
            fib2.value = new Stream<>(fib1.value.headS() + fib2.value.headS(), Thunk.delay(fib2Suppiler.value));
            return fib1.value;
        };
        fib1.value = new Stream<>(0, Thunk.delay(fib1Supplier.value));
        fib2.value = new Stream<>(1, Thunk.delay(fib2Suppiler.value));
        return fib1.value;
    }

    // Return the stream of all prime numbers.
    public static Stream<Integer> primeS() {
        return Stream.fromS(2).filterS(Stream::isPrime).get();
    }

    private static boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
