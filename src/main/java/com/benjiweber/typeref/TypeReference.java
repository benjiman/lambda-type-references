package com.benjiweber.typeref;

import java.util.function.Consumer;

public interface TypeReference<T> extends Newable<T> {
    T typeIs(T t);
    default Consumer<T> consumer() {
        return this::typeIs;
    }
}
