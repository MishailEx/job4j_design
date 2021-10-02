package ru.job4j.search;

import ru.job4j.io.SearchFiles;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Find {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Not argument for search");
        }
        if (args.length < 4) {
            throw new IllegalArgumentException("missing arguments. Must be -d=directory"
                    + " -n=what search -t=type search -o=where write result");
        }
        ArgsName arg = ArgsName.of(args);
        Path root = Paths.get(arg.get("d"));
        List<Path> toWrite = search(root, predicate(arg.get("t"), arg.get("n")));
        if (toWrite.isEmpty()) {
            toWrite.add(Paths.get("No such files found"));
        }
        writeInFile(arg.get("o"), toWrite);
    }

    private static Predicate<Path> predicate(String typeFind, String file) {
        Predicate<Path> predicate = null;
        if (typeFind.equals("mask") || typeFind.equals("name") || typeFind.equals("regex")) {
            if (typeFind.equals("mask")) {
                predicate = p -> p.toFile().getName().endsWith(file);
            }
            if (typeFind.equals("name")) {
                predicate = p -> p.toFile().getName().equals(file);
            }
            if (typeFind.equals("regex")) {
                predicate = p -> p.toFile().getName().matches("(.*)" + file + "(.*)");
            }
            } else {
                throw new IllegalArgumentException("Search type must be : mask or name or regex");
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