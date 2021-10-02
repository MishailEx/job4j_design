package ru.job4j.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "dog")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dog {
    @XmlAttribute
    private int age;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private boolean sex;
    private Parents parents;
    @XmlElementWrapper
    @XmlElement(name = "crimes")
    private String[] crimes;

    public Dog() {
    }

    public Dog(int age, String name, boolean sex, Parents parents, String[] crimes) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.parents = parents;
        this.crimes = crimes;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public boolean isSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "Dog{" + "age=" + age
                + ", name='" + name + '\''
                + ", sex=" + sex
                + ", parents=" + parents
                + ", crimes=" + Arrays.toString(crimes) + '}';
    }

    public static void main(String[] args) throws JAXBException {
        Dog dog = new Dog(2, "bobik", true,
                new Parents("taksa", "bolonka"),
                new String[] {"gnaw", "steals"});
        JSONObject jsonParents = new JSONObject("{\"mather\" : \"bolonka\", \"father\" : \"taksa\"}");
        List<String> list = new ArrayList<>();
        list.add("steels");
        list.add("gnaw");
        JSONArray jsonCrimes = new JSONArray(list);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("age", dog.getAge());
        jsonObject.put("sex", dog.isSex());
        jsonObject.put("name", dog.getName());
        jsonObject.put("parents", jsonParents);
        jsonObject.put("crimes", jsonCrimes);
        System.out.println(jsonObject);
        System.out.println(new JSONObject(dog));


//        JAXBContext context = JAXBContext.newInstance(Dog.class);
//        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//        String xml = "";
//        try (StringWriter stringWriter = new StringWriter()) {
//            marshaller.marshal(dog, stringWriter);
//            xml = stringWriter.getBuffer().toString();
//            System.out.println(xml);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        try (StringReader reader = new StringReader(xml)) {
//            Dog result = (Dog) unmarshaller.unmarshal(reader);
//            System.out.println(result);
//        }

//        final Gson gson = new GsonBuilder().create();
//        System.out.println(gson.toJson(dog));
//
//        String str = "{ \"age\":2,\"name\":\"bobik\",\"sex\":\"true\","
//                + "\"parents\":{\"father\":\"taksa\",\"mather\":\"bolonka\"},"
//                + "\"crimes\":[\"gnaw\",\"steels\"]}";
//        Dog dogFromJSON = gson.fromJson(str, Dog.class);
//        System.out.println(dogFromJSON);
    }
}
