package com.benjiweber;

import com.benjiweber.typeref.MethodAwareFunction;
import org.junit.Test;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class LambdaName {

    @Test
    public void lambda_names_there() throws NoSuchMethodException {
        MethodAwareFunction<String, String> f = bertie -> bertie + "hello";
        MethodAwareFunction<String,String> f2 = i->i;
        MethodAwareFunction<String,String> f3 = i->i;
        MethodAwareFunction<Integer,Integer> f4 = i->i;
        MethodAwareFunction<List<String>,List<String>> f5 = i->i;
        asList(f2,f3,f4,f5).forEach(fun -> {
            System.out.println(fun);
            System.out.println(getLambdaMethod(fun.serialized()));
        });

        System.out.println(f2);
        System.out.println(f3);
        System.out.println(f4);


        Method m = getLambdaMethod(f.serialized());

        String name = m.getParameters()[0].getName();

        System.out.println(f);
        assertEquals("bertie", name);

    }

    public static Method getLambdaMethod(SerializedLambda lambda) {
        try {
            String implClassName = lambda.getImplClass().replace('/', '.');
            Class<?> implClass = Class.forName(implClassName);

            String lambdaName = lambda.getImplMethodName();

            for (Method m : implClass.getDeclaredMethods()) {
                if (m.getName().equals(lambdaName)) {
                    return m;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Lambda Method not found");
    }
}
