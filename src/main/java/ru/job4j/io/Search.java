package ru.job4j.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("Root folder is null. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        Path start = Paths.get(args[0]);
        search(start, ".txt").forEach(System.out::println);
    }

    public static List<Path> search(Path root, String extension) throws IOException {
        if (Files.size(root) == 0) {
            throw new IllegalArgumentException("Root folder is null. Usage java -jar dir.jar ROOT_FOLDER.");
        }
        if (!extension.startsWith(".")) {
            throw new IllegalArgumentException("the extension must start with \".\"");
        }
        SearchFiles searcher = new SearchFiles(extension);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }
}