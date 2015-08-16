package com.benjiweber;

import com.benjiweber.typeref.Parameters;
import com.benjiweber.typeref.TypeReference;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class Examples {
    public static <T> T create(TypeReference<T> type) {
        return type.newInstance();
    }
    @Test
    public void creating_a_list() {
        ArrayList<String> list = create(i->i);
        list.add("hello");
        list.add("world");

        assertEquals(asList("hello","world"), list);
    }

    @Test
    public void creating_a_map() {
        HashMap<String, Integer> map = create(i->i);
        map.put("hello",1);
        map.put("world",2);

        assertEquals(Integer.valueOf(1), map.get("hello"));
        assertEquals(Integer.valueOf(2), map.get("world"));
    }

    @Test
    public void variable_ref() {
        TypeReference<String> ref = i->i;
        assertEquals("java.lang.String", ref.type().getName());
    }

    @Test
    public void variable_ref_generic_type() {
        TypeReference<ArrayList<String>> ref = i->i;
        ArrayList<String> strings = ref.newInstance();
        assertEquals(Collections.emptyList(), strings);
    }

    @Test
    public void parameter_object() {
        List<String> result = foo(list -> {
            list.add("Hello");
            list.add("World");
        });

        assertEquals(asList("Hello foo", "World foo"), result);
    }

    public static List<String> foo(Parameters<ArrayList<String>> params) {
        return params.get().stream().map(s -> s + " foo").collect(toList());
    }
    public static void foo(String aAparam) {
        Function<String, String> f = bobBobBob -> "hello";
        f.apply((String)(Object)5);
    }


}
