package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Analizy {
    private String marker = "200";
    private String key;
    public void unavailable(String source, String target) {
        Map<String, String> log = new LinkedHashMap<>();
        try (BufferedReader out = new BufferedReader(new FileReader(source))) {
            out.lines()
                    .map(e -> e.split("\\s"))
                    .forEach(a -> {
                        if ((!marker.equals("400") && !marker.equals("500"))
                                && (a[0].equals("400") || a[0].equals("500"))) {
                            marker = a[0];
                            key = a[1];
                            log.put(a[1], null);
                        }
                        if ((!marker.equals("200") && !marker.equals("300"))
                                && (a[0].equals("200") || a[0].equals("300"))) {
                            log.put(key, a[1]);
                            marker = a[0];
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        writeToFile(target, log);
    }

    private static void writeToFile(String target, Map<String, String> log) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(target))) {
            for (Map.Entry<String, String> entry: log.entrySet()) {
                out.println(entry.getKey() + ";" + entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analizy analizy = new Analizy();
        analizy.unavailable("server.log", "unavailable.csv");
    }
}