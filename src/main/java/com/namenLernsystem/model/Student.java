package com.namenLernsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author Hermann Witte
 * @since 3.10.2022
 * @version 1.0
 * */
@Entity
public class Student {
    @Id
    private String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private String className;
    private String path;

    public Student(String firstName, String lastName, String className, String path) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.className = className;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
