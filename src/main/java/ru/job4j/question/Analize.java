package ru.job4j.question;

import java.util.*;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int add = 0;
        int change = 0;
        int del = 0;
        Map<Integer, User> prevMap = new HashMap<>();
        for (User user: previous) {
            prevMap.put(user.getId(), user);
            if (!current.contains(user)) {
                del++;
            }
        }
        for (User now: current) {
            if (!previous.contains(now)) {
                add++;
            }
            if (prevMap.containsKey(now.getId())) {
                if (prevMap.get(now.getId()).getId() == now.getId()
                        && !prevMap.get(now.getId()).getName().equals(now.getName())) {
                    change++;
                }
            }
        }
        return new Info(add, change, del);
    }

}