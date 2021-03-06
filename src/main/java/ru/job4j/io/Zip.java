package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void archiving(String path, Predicate<Path> predicate, String nameZip) throws IOException {
        Path pas = Path.of(path);
        if (path == null || predicate == null || nameZip == null) {
            throw new IllegalArgumentException();
        }
        if (!Files.exists(pas)) {
            throw new IllegalArgumentException(String.format("Not exist %s", Path.of(path)));
        }
        List<Path> all = Search.search(Path.of(path), predicate);
        List<File> save = all.stream().map(Path::toFile).collect(Collectors.toList());

        packFiles(save, new File(nameZip));
    }

    public static void packFiles(List<File> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (File file : sources) {
                zip.putNextEntry(new ZipEntry(file.getPath()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(file))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ArgsName argsName = ArgsName.of(args);
        archiving(argsName.get("d"), p -> !p.toString().endsWith(argsName.get("e")), argsName.get("o"));


    }
}