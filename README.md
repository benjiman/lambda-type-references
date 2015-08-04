# lambda-type-references

Type references using lambdas

http://benjiweber.com/blog/2015/08/04/lambda-type-references/

```java
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
```