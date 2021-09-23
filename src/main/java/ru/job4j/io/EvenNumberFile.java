package ru.job4j.io;

import java.io.FileInputStream;

public class EvenNumberFile {
    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("even.txt")) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = in.read()) != -1) {
                text.append((char) read);
            }
            String[] str = text.toString().split(System.lineSeparator());
            boolean rsl;
            for (String string : str) {
                if (Integer.parseInt(string) % 2 == 0) {
                    rsl = true;
                    System.out.println(string + rsl);
                } else {
                    rsl = false;
                    System.out.println(string + rsl);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}