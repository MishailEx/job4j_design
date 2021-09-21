package ru.job4j.question;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int add = 0;
        int change = 0;
        int del = 0;
        for (User now: current) {
            if (!previous.contains(now)) {
                add++;
            }
            for (User prev: previous) {
                if (now.getId() == prev.getId()
                        && !now.getName().equals(prev.getName())) {
                    change++;
                }

            }
        }
        for (User user: previous) {
            if (!current.contains(user)) {
                del++;
            }
        }
        return new Info(add, change, del);
    }

}