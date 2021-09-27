package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader out = new BufferedReader(
                new FileReader(path))) {
            out.lines()
                    .filter(e -> e.contains("="))
                    .map(s -> s.split("="))
                    .forEach(a -> {
                        try {
                            if (a[0].trim().isEmpty()) {
                                throw new IllegalArgumentException();
                            } else {
                                values.put(a[0], (a.length > 1 ? a[1] : ""));
                            }
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("app.properties"));
        Config config = new Config("app.properties");
        config.load();
        for (Map.Entry entry: config.values.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
        }

    }
}