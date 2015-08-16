package com.benjiweber.typeref;

public interface Newable<T> extends MethodFinder {
    default Class<T> type() {
        return (Class<T>)parameter(0).getType();
    }
    default T newInstance() {
        try {
            return type().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
