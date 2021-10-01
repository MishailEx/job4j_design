package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String name = "Mihail";
        int age = 30;
        boolean man = true;
        double temperature = 36.6;
        char sex = 'M';
        short legs = 2;
        float height = 25.6f;
        long number = 123456;
        LOG.debug("User info name : {}, age : {}, man : {}, "
                + "temperature : {}, sex : {}, legs : {}, height : {}, number : {}",
                name, age, man, temperature, sex, legs, height, number);
    }
}
