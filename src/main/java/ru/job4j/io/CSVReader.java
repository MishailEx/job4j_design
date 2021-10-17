package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVReader {
    public static void handle(ArgsName argsName) throws Exception {
        try (Scanner scannerLine = new Scanner(new File(argsName.get("path")))) {
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
    }

    private static ArgsName validateArgument(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("missing arguments. Must be  -path=xx"
                    + " -delimiter=xx  -out=stdout or file -filter=xx");
        }
        ArgsName arg = ArgsName.of(args);
        if (arg.get("path") == null || arg.get("delimiter") == null
                || arg.get("out") == null || arg.get("filter") == null) {
            throw new IllegalArgumentException("not argument");
        }
        if (!Files.exists(Path.of(arg.get("path")))) {
            throw new IllegalArgumentException(arg.get("path") + "not found");
        }
        return arg;
    }

    private static void showOnConsole(List<Integer> in,  List<String> csv) {
        for (int i = 0; i < in.size(); i++) {
            if (i == in.size() - 1) {
                System.out.print(csv.get(in.get(i)));
                break;
            }
            System.out.print(csv.get(in.get(i)) + ";");
        }
        System.out.println();
    }

    private static void writeInFile(String path, List<Integer> in, List<String> csv) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(path, Charset.forName("WINDOWS-1251"), true))) {
            for (int i = 0; i < in.size(); i++) {
                if (i == in.size() - 1) {
                    pw.write(csv.get(in.get(i)));
                    break;
                }
                pw.write(csv.get(in.get(i)) + ";");
            }
            pw.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ArgsName argsName = validateArgument(args);
        handle(argsName);
    }
}