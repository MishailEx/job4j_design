package ru.job4j.serialization;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "parents")
public class Parents {
    @XmlAttribute
    private String father;
    @XmlAttribute
    private String mather;

    public Parents() {
    }

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
