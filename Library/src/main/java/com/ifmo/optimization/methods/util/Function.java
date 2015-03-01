package com.ifmo.optimization.methods.util;

@FunctionalInterface
public interface Function<T1, T2, R> {

    public R apply(T1 t1, T2 t2);

}
