package com.benjiweber;

import com.benjiweber.typeref.NamedValue;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class PuppetExample {


    @Test
    public void puppet_example() {
        String manifest = new Apache().toString();
        assertEquals(
                "class Apache {\n" +
                "\n" +
                "\tfile{\n" +
                "\t\tname\t=> \"/etc/httpd/httpd.conf\",\n" +
                "\t\tsource\t=> template(httpd.tpl),\n" +
                "\t\towner\t=> root,\n" +
                "\t\tgroup\t=> root,\n" +
                "\t}\n" +
                "\tcron{\n" +
                "\t\tname\t=> \"logrotate\",\n" +
                "\t\tcommand\t=> \"/usr/sbin/logrotate\",\n" +
                "\t\tuser\t=> root,\n" +
                "\t\thour\t=> \"2\",\n" +
                "\t\tminute\t=> \"*/10\",\n" +
                "\t}\n" +
                "\n" +
                "}",
                manifest
        );
    }


static class Apache extends PuppetClass {{
    file(
        name -> "/etc/httpd/httpd.conf",
        source -> template("httpd.tpl"),
        owner -> root,
        group -> root
    );

    cron (
        name -> "logrotate",
        command -> "/usr/sbin/logrotate",
        user -> root,
        hour -> "2",
        minute -> "*/10"
    );
}}

    interface PuppetParam<T> extends NamedValue<T> {
        default String asString() {
            return name() + "\t=> " + quote(value());
        }
        default String quote(Object value) {
            return value instanceof String
                    ? "\"" + value + "\""
                    : value.toString();
        }
    }

    interface PuppetType {
        StringBuilder builder();
        default void append(String type, PuppetParam<?>... params) {
            builder().append("\n\t");
            builder().append(type).append("{\n");
            asList(params)
                .stream()
                .map(PuppetParam::asString)
                .map(value -> "\t\t" + value + ",\n")
                .forEach(builder()::append);
            builder().append("\t}");
        }
    }
    interface PuppetFile extends PuppetType {
        default void file(PuppetParam<?>... params) {
            append("file", params);
        }
    }
    interface PuppetCron extends PuppetType {
        default void cron(PuppetParam<?>... params) {
            append("cron", params);
        }
    }
    public static class PuppetClass implements PuppetFile, PuppetCron {
        StringBuilder builder = new StringBuilder();

        public StringBuilder builder() {
            return builder;
        }

        static PuppetLiteral template(String name) {
            return new PuppetLiteral("template(" + name + ")");
        }

        static PuppetLiteral root = new PuppetLiteral("root");

        @Override
        public String toString() {
            builder.insert(0, "class " + getClass().getSimpleName() + " {\n");
            builder.append("\n\n}");
            return builder.toString();

        }
    }

    static class PuppetLiteral {
        String value;

        public PuppetLiteral(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}

