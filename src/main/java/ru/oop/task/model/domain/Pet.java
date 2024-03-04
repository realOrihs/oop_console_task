package ru.oop.task.model.domain;

public class Pet {
    private Integer id;
    private String name;
    private String breed;
    private Integer age;


    public Pet(Integer id, String name, String breed, Integer age) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Object getFieldValue(String fieldName) {
        return switch (fieldName) {
            case "id" -> id;
            case "name" -> name;
            case "breed" -> breed;
            case "age" -> age;
            default -> null;
        };
    }
}

