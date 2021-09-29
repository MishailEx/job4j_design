package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVReader {
    public static void handle(ArgsName argsName) throws Exception {
        if (!Files.exists(Path.of(argsName.get("path")))) {
            throw new IllegalArgumentException("File not found");
        }
        Scanner scannerLine = new Scanner(new File(argsName.get("path")));
        List<String> filterWords = Arrays.asList(argsName.get("filter").split(","));
        List<Integer> in = new ArrayList<>();
        List<String> csv = new ArrayList<>();
        while (scannerLine.hasNextLine()) {
            Scanner scannerEl = new Scanner(scannerLine.nextLine()).useDelimiter(argsName.get("delimiter"));
            while (scannerEl.hasNext()) {
                String f = scannerEl.next();
                csv.add(f);
                if (filterWords.contains(f)) {
                    in.add(csv.indexOf(f));
                }
            }
            if (argsName.get("out").equals("stdout")) {
                showOnConsole(in, csv);
            } else {
                writeInFile(argsName.get("out"), in, csv);
            }
            csv.clear();
        }
    }

    private static void showOnConsole(List<Integer> in,  List<String> csv) {
        for (int i = 0; i < in.size(); i++) {
            if (i == in.size() - 1) {
                System.out.print(csv.get(i));
                break;
            }
            System.out.print(csv.get(i) + ";");
        }
        System.out.println();
    }

    private static void writeInFile(String path, List<Integer> in, List<String> csv) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(path, Charset.forName("WINDOWS-1251"), true))) {
            for (int i = 0; i < in.size(); i++) {
                if (i == in.size() - 1) {
                    pw.write(csv.get(i));
                    break;
                }
                pw.write(csv.get(i) + ";");
            }
            pw.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ArgsName argsName = ArgsName.of(args);
        handle(argsName);
    }
}