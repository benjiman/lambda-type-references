package com.benjiweber.typeref;

public interface Parameters<T> extends NewableConsumer<T> {
    default T get() {
        T t = newInstance();
        accept(t);
        return t;
    }
}