package andy.zhu.codewars.functionalstreams;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by AndyZhu on 2017/5/18.
 */
public class Thunk<T> {

    private boolean isEvaluated;
    private T mValue;
    private Supplier<T> mSupplier;

    private Thunk(T value) {
        isEvaluated = true;
        mValue = value;
    }

    private Thunk(Supplier<T> supplier) {
        isEvaluated = false;
        mSupplier = supplier;
    }

    public T get() {
        if (!isEvaluated) {
            mValue = mSupplier.get();
            isEvaluated = true;
        }
        return mValue;
    }

    public <R> Thunk<R> chain(Function<T, R> function) {
        return new Thunk<>(() -> function.apply(get()));
    }

    public static <T> Thunk<T> now(T value) {
        return new Thunk<>(value);
    }

    public static <T> Thunk<T> delay(Supplier<T> supplier) {
        return new Thunk<T>(supplier);
    }
}
