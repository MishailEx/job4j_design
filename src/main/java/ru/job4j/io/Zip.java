package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void archiving(String path, String extension, String nameZip) throws IOException {
        Path pas = Path.of(path);
        if (path == null || extension == null || nameZip == null) {
            throw new IllegalArgumentException();
        }
        if (!Files.exists(pas)) {
            throw new IllegalArgumentException(String.format("Not exist %s", Path.of(path)));
        }
        List<Path> allFile = Files.walk(Path.of(path))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        List<Path> del = Search.search(pas, extension);
        allFile.removeAll(del);
        List<File> save = allFile.stream().map(Path::toFile).collect(Collectors.toList());
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
        archiving(argsName.get("d"), argsName.get("e"), argsName.get("o"));


    }
}