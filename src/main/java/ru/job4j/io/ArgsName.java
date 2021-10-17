package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        return values.get(key);
    }

    private void parse(String[] args) {
        validate(args);
        for (String s: args) {
            if (s.startsWith("-")) {
               s = s.substring(1);
            }
            String[] str = s.trim().split("=");
            if (str[0].isEmpty() || str.length != 2) {
                throw new IllegalArgumentException("Empty field");
            }
            values.put(str[0], (str[1]));
        }
    }

    private static void validate(String[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("File is empty");
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));

    }
}