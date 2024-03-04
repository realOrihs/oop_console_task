package ru.oop.task.model.domain;

public class User{
    private Integer id;
    private String name;
    private String lastName;
    private Integer age;
    private Integer petId;

    public User(Integer id, String name, String lastName, Integer age, Integer petId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.petId = petId;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Object getFieldValue(String fieldName) {
        return switch (fieldName) {
            case "id" -> id;
            case "name" -> name;
            case "last_name" -> lastName;
            case "age" -> age;
            case "pet_id" -> petId;
            default -> null;
        };
    }

}

