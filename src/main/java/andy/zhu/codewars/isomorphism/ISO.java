package andy.zhu.codewars.isomorphism;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by Andy.Zhu on 2017/8/7.
 * https://www.codewars.com/kata/5922543bf9c15705d0000020/train/java
 */
/*
 * so, when are two type, `a` and `b`, considered equal?
 * a definition might be, it is possible to go from `a` to `b`,
 * and from `b` to `a`.
 * Going a roundway trip should leave you the same value.
 * Unfortunately it is virtually impossible to test this in Haskell.
 * This is called ISO.
 */
public abstract class ISO<A, B> {
    /* go backward */
    abstract A bw(final B b);

    /* go forward */
    abstract B fw(final A a);

    abstract static class Either<L, R> {
        public abstract <T> T pm(Function<L, T> lt, Function<R, T> rt);

        static <L, R> Either<L, R> left(L l) {
            return new Either<L, R>() {
                @Override
                public <T> T pm(Function<L, T> lt, Function<R, T> rt) {
                    return lt.apply(l);
                }

                @Override
                public boolean equals(Object rhs) {
                    return ((Either<L, R>) rhs).<Boolean>pm(l::equals, rr -> false);
                }
            };
        }

        static <L, R> Either<L, R> right(R r) {
            return new Either<L, R>() {
                @Override
                public <T> T pm(Function<L, T> lt, Function<R, T> rt) {
                    return rt.apply(r);
                }

                @Override
                public boolean equals(Object rhs) {
                    return ((Either<L, R>) rhs).<Boolean>pm(ll -> false, r::equals);
                }
            };
        }
    }

    final static class Tuple<A, B> {
        A a;
        B b;

        Tuple(A a, B b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object rhs) {
            Tuple<A, B> ab = (Tuple<A, B>) rhs;
            return a.equals(ab.a) && b.equals(ab.b);
        }
    }

    final static class Unit {
        public static Unit INSTANCE = new Unit();

        private Unit() {
        }
    }

    static abstract class Void {
        public abstract <T> T absurd();
    }

    private static <A, B> ISO<A, B> iso(
            Function<A, B> forward, Function<B, A> backward) {
        return new ISO<A, B>() {
            @Override public A bw(B b) { return backward.apply(b); }
            @Override public B fw(A a) { return forward.apply(a); }
        };
    }

    /* given ISO a b, we can go from a to b */
    static <A, B> Function<A, B> subStL(final ISO<A, B> iso) {
        return iso::fw;
    }

    /* and vise versa */
    static <A, B> Function<B, A> subStR(final ISO<A, B> iso) {
        return iso::bw;
    }

    /* There can be more than one ISO a b */
    static ISO<Boolean, Boolean> isoBool() {
        return refl();
    }

    static ISO<Boolean, Boolean> isoBoolNot() {
        return iso(a -> !a, b -> !b);
    }

    /* isomorphism is reflexive */
    static <A> ISO<A, A> refl() {
        return iso(a -> a, a -> a);
    }

    /* isomorphism is symmetric */
    static <A, B> ISO<A, B> symm(final ISO<B, A> iso) {
        return iso(iso::bw, iso::fw);
    }

    /* isomorphism is transitive */
    static <A, B, C> ISO<A, C> trans(
            final ISO<A, B> ab, final ISO<B, C> bc) {
        return iso(a -> bc.fw(ab.fw(a)), c -> ab.bw(bc.bw(c)));
    }

    /* We can combine isomorphism */
    static <A, B, C, D> ISO<Tuple<A, C>, Tuple<B, D>> isoTuple(
            final ISO<A, B> ab, final ISO<C, D> cd) {
        return iso(ac -> new Tuple<>(ab.fw(ac.a), cd.fw(ac.b)), bd -> new Tuple<>(ab.bw(bd.a), cd.bw(bd.b)));
    }

    static <A, B> ISO<Stream<A>, Stream<B>> isoStream(
            final ISO<A, B> iso) {
        return iso(sa -> sa.map(iso::fw), sb -> sb.map(iso::bw));
    }

    static <A, B> ISO<Optional<A>, Optional<B>> isoOptional(
            final ISO<A, B> iso) {
        return iso(oa -> oa.map(iso::fw), ob -> ob.map(iso::bw));
    }

    static <A, B, C, D> ISO<Either<A, C>, Either<B, D>> isoEither(
            final ISO<A, B> ab, final ISO<C, D> cd) {
        return iso(
                eac -> new Either<B, D>() {
                    @Override
                    public <T> T pm(Function<B, T> lt, Function<D, T> rt) {
                        return eac.pm(a -> lt.apply(ab.fw(a)), c -> rt.apply(cd.fw(c)));
                    }
                },
                ebd -> new Either<A, C>() {
                    @Override
                    public <T> T pm(Function<A, T> lt, Function<C, T> rt) {
                        return ebd.pm(b -> lt.apply(ab.bw(b)), d -> rt.apply(cd.bw(d)));
                    }
                });
    }

    /*
     * Going another way is hard (and is generally impossible)
     * Remember, for all valid ISO, converting and converting back
     * is the same as the original value.
     * You need this to prove some case are impossible.
     */
    static <A, B> ISO<A, B> isoUnOptional(
            final ISO<Optional<A>, Optional<B>> iso) {
        return iso(a -> iso.fw(Optional.of(a)).orElse(null),
                   b -> iso.bw(Optional.of(b)).orElse(null));
    }

    // We cannot have
    // isoUnEither ::
    // ISO (Either a b) (Either c d) -> ISO a c -> ISO b d.
    // Note that we have
    static ISO<Either<Stream<Unit>, Unit>, Either<Stream<Unit>, Void>>
    isoEU() {
        ISO<Stream<Unit>, Stream<Unit>> isoab = iso(s -> s, s -> s);
        Void voidInstance = new Void() {
            @Override
            public <T> T absurd() {
                return null;
            }
        };
        ISO<Unit, Void> isocd = iso(unit -> voidInstance, aVoid -> Unit.INSTANCE);
        return isoEither(isoab, isocd);
    }
    // where (), the empty tuple, has 1 value, and Void has 0 value
    // If we have isoUnEither,
    // We have ISO () Void by calling isoUnEither isoEU
    // That is impossible, since we can get a Void by
    // substL on ISO () Void
    // So it is impossible to have isoUnEither

    static <A, B, C, D> ISO<Function<A, C>, Function<B, D>>
    isoFunc(final ISO<A, B> ab, final ISO<C, D> cd) {
        return iso(fac -> (b -> cd.fw(fac.apply(ab.bw(b)))),
                   fbd -> (a -> cd.bw(fbd.apply(ab.fw(a)))));
    }

    /* And we have isomorphism on isomorphism! */
    static <A, B> ISO<ISO<A, B>, ISO<B, A>> isoSymm() {
        return iso(isoab -> iso(isoab::bw, isoab::fw),
                   isoba -> iso(isoba::bw, isoba::fw));
    }
}