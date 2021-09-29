package ru.job4j;

import com.sun.source.tree.LabeledStatementTree;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConsoleChat {

    private final String path;
    private final String botAnswers;
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        List<String> log = new ArrayList<>();
        List<String> phrase = readPhrases();
        Random random = new Random();
        int stop = 0;
        String console = in.readLine();
        while (!console.equals(OUT)) {
            log.add("Пользователь - " + console + " ");
            if (console.equals(STOP)) {
                stop = 1;
            }
            if (console.equals(CONTINUE)) {
                stop = 0;
            }
            if (stop == 0) {
                String bot = phrase.get(random.nextInt(phrase.size()));
                System.out.println(bot);
                log.add("Бот - " + bot + " ");
            }
            console = in.readLine();
        }
        saveLog(log);
    }

    private List<String> readPhrases() {
        List<String> phrase = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(botAnswers, Charset.forName("WINDOWS-1251")))) {
            br.lines().forEach(phrase::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return phrase;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(path, Charset.forName("WINDOWS-1251"), true))) {
            log.forEach(pw::write);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ConsoleChat cc = new ConsoleChat("C:\\projects\\log.txt", "C:\\projects\\lll.txt");
        cc.run();
    }
}

