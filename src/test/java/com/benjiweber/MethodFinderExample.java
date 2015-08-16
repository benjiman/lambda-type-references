package com.benjiweber;

import com.benjiweber.typeref.MethodAwarePredicate;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;

public class MethodFinderExample {

    @Test
    public void find_lambda_synthetic_method() {
        MethodAwarePredicate<String> p = s -> s.toLowerCase().equals("hello");
        assertEquals(MethodFinderExample.class.getName(), p.method().getDeclaringClass().getName());
        assertThat(p.method().getName(), startsWith("lambda$find_lambda_synthetic_method$"));
    }

    @Test
    public void execute_lambda_synthetic_method() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Runnable r = () -> {
            try {
                MethodAwarePredicate<Integer> p = i -> i < 5;

                assertFalse((Boolean) p.method().invoke(null, 5));
                assertTrue((Boolean) p.method().invoke(null, 3));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        r.run();
    }


}
