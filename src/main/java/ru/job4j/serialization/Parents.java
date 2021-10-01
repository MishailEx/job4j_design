package ru.job4j.serialization;

public class Parents {
    private String father;
    private String mather;

    public Parents(String father, String mather) {
        this.father = father;
        this.mather = mather;
    }

    @Override
    public String toString() {
        return "Parents{" + "father='" + father + '\''
                + ", mather='" + mather + '\'' + '}';
    }
}
