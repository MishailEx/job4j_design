package ru.job4j.search;

import ru.job4j.io.SearchFiles;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Find {
    public static void main(String[] args) throws IOException {
        ArgsName arg = validateArgument(args);
        Path root = Paths.get(arg.get("d"));
        List<Path> toWrite = search(root, predicate(arg.get("t"), arg.get("n")));
        if (toWrite.isEmpty()) {
            toWrite.add(Paths.get("No such files found"));
        }
        writeInFile(arg.get("o"), toWrite);
    }

    private static boolean validateT(String typeFind) {
        if (typeFind.equals("mask") || typeFind.equals("name") || typeFind.equals("regex")) {
            return true;
        } else {
            throw new IllegalArgumentException("Search type must be : mask or name or regex");
        }
    }

    private static ArgsName validateArgument(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Not argument for search");
        }
        if (args.length != 4) {
            throw new IllegalArgumentException("missing arguments. Must be -d=directory"
                    + " -n=what search -t=type search -o=where write result");
        }
        ArgsName arg = ArgsName.of(args);
        if (arg.get("d") == null || arg.get("n") == null
                || arg.get("t") == null || arg.get("o") == null) {
            throw new IllegalArgumentException("not argument");
        }
        if (!Files.isDirectory(Path.of(arg.get("d")))) {
            throw new IllegalArgumentException(arg.get("d") + "not is directory");
        }
        if (arg.get("d").isEmpty() || arg.get("n").isEmpty()
                || arg.get("t").isEmpty() || arg.get("o").isEmpty()) {
            throw new IllegalArgumentException("not argument value");
        }
        return arg;
    }

    private static Predicate<Path> predicate(String typeFind, String file) {
        Predicate<Path> predicate = null;
        if (validateT(typeFind)) {
            if (typeFind.equals("mask")) {
                predicate = p -> {
                    String mask = file;
                    mask.replace("*", ".*")
                            .replace("?", "\\w{1}");
                    Pattern pattern = Pattern.compile(mask);
                    Matcher matcher = pattern.matcher(p.toFile().getName());
                    return matcher.find();
                };
            }
            if (typeFind.equals("name")) {
                predicate = p -> p.toFile().getName().equals(file);
            }
            if (typeFind.equals("regex")) {
                predicate = p -> {
                    Pattern pattern = Pattern.compile(file);
                    Matcher matcher = pattern.matcher(p.toFile().getName());
                    return matcher.find();
                };
            }
        }
        return predicate;
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        if (Files.size(root) == 0) {
            throw new IllegalArgumentException("Root folder is null. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        if (condition == null) {
            throw new IllegalArgumentException("what files to look for");
        }
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static void writeInFile(String path, List<Path> in) {
        try (PrintWriter pw = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(path)))) {
            for (Path p : in) {
                pw.write(p.toString());
                pw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}