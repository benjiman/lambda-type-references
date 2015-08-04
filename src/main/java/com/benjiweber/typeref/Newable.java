package com.benjiweber.typeref;

import java.util.function.Consumer;

public interface Newable<T> {
    Consumer<T> consumer();
    static final class DummyType { private DummyType() {} };
    default String className() {
        try {
            consumer().accept((T) new DummyType());
            throw new UnableToGuessClassException();
        } catch (ClassCastException e) {
            return e.getMessage().replaceAll(".*to ", "");
        }
    }
    default Class<T> type() {
        try {
            return (Class<T>)Class.forName(className());
        } catch (Exception e) {
            throw new UnableToGuessClassException();
        }
    }
    default T newInstance() {
        try {
            return type().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    class UnableToGuessClassException extends RuntimeException {}
}
