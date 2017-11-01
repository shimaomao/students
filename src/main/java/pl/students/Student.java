package pl.students;

import java.util.UUID;

public class Student {
    private String id;
    private String name;
    private String lastName;

    public Student() {
        this.id = UUID.randomUUID().toString();
    }

    public Student(String name, String lastName) {
        this();
        this.name = name;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
