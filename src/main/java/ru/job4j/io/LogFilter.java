package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LogFilter {
    public static List<String> filter(String file) {
        List<String> tmp;
        List<String> save = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader("log.txt"))) {
            tmp = in.lines()
                    .filter(e -> e.contains("404"))
                    .collect(Collectors.toList());
            for (String str : tmp) {
                String[] se = str.split("\\s");
                if (se[se.length - 2].equals("404")) {
                    save.add(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return save;
    }

    public static void main(String[] args) {
        List<String> log = filter("log.txt");
        System.out.println(log);
    }
}