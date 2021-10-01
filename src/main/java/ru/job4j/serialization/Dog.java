package ru.job4j.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

public class Dog {
    private int age;
    private String name;
    private boolean sex;
    private Parents parents;
    private String[] crimes;

    public Dog(int age, String name, boolean sex, Parents parents, String[] crimes) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.parents = parents;
        this.crimes = crimes;
    }

    @Override
    public String toString() {
        return "Dog{" + "age=" + age
                + ", name='" + name + '\''
                + ", sex=" + sex
                + ", parents=" + parents
                + ", crimes=" + Arrays.toString(crimes) + '}';
    }

    public static void main(String[] args) {
        Dog dog = new Dog(2, "bobik", true,
                new Parents("taksa", "bolonka"),
                new String[] {"gnaw", "steals"});
        final Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(dog));

        String str = "{ \"age\":2,\"name\":\"bobik\",\"sex\":\"true\","
                + "\"parents\":{\"father\":\"taksa\",\"mather\":\"bolonka\"},"
                + "\"crimes\":[\"gnaw\",\"steels\"]}";
        Dog dogFromJSON = gson.fromJson(str, Dog.class);
        System.out.println(dogFromJSON);
    }
}
