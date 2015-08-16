package com.benjiweber.typeref;

import com.benjiweber.typeref.NewableConsumer;

import java.util.function.Consumer;

public class NameOf {
    public static void main(String... args) {
        new NameOf().aMethod(null);
    }
    public void aMethod(String aParam) {
        throw new NullPointerException(nameof(this::aMethod, 0));
    }

    public static <T> String nameof(NewableConsumer<T> method, int arg) {
        try {
            StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[1];
            Class<?> cls = Class.forName(caller.getClassName());
            return cls.getDeclaredMethod(caller.getMethodName(), method.type()).getParameters()[arg].getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
