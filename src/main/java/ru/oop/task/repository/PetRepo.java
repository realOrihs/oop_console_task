package ru.oop.task.repository;

import ru.oop.task.model.domain.Database;
import ru.oop.task.model.domain.Pet;
import ru.oop.task.model.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PetRepo extends BaseRepository<Pet> {

    private final List<String> existingFields = new ArrayList<>();
    public PetRepo(Database<Pet> petDb) {
        super(petDb);
        existingFields.add("id");
        existingFields.add("name");
        existingFields.add("breed");
        existingFields.add("age");
    }

    public boolean checkIfFieldExist(String field) {
        if (existingFields.contains(field)) {
            return true;
        } else {
            System.out.println("Поле " + field + " не существует.");
            return false;
        }
    }

    @Override
    public void update(Pet pet, String fieldToUpdate, String valueToUpdate) {
        switch (fieldToUpdate) {
            case ("name") -> pet.setName(valueToUpdate);
            case ("breed") -> pet.setBreed(valueToUpdate);
            case ("age") -> pet.setAge(Integer.parseInt(valueToUpdate));
            default -> System.out.println("Поле " + fieldToUpdate + " не существует.");
        }

    }

    public boolean petExists(int petId) {
        List<Pet> allPets = selectAll();
        for (Pet pet : allPets) {
            if (pet.getId() == petId) {
                return true;
            }
        }
        return false;
    }

    //фильтруем петов по условию, полю и значению
//    public List<Pet> filterPets(String fieldName, String value, String condition) {
//        List<Pet> petList = selectAll();
//        List<Pet> filteredPets = new ArrayList<>();
//        if (!Objects.equals(fieldName, "id")) {
//            for (Pet pet : petList)
//                switch (condition) {
//                    case ("=") -> {
//                        if (pet.getFieldValue(fieldName).equals(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case ("!=") -> {
//                        if (!pet.getFieldValue(fieldName).equals(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case (">=") -> {
//                        if (Integer.parseInt(pet.getFieldValue(fieldName).toString()) >= Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case ("<=") -> {
//                        if (Integer.parseInt(pet.getFieldValue(fieldName).toString()) <= Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case (">") -> {
//                        if (Integer.parseInt(pet.getFieldValue(fieldName).toString()) > Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case ("<") -> {
//                        if (Integer.parseInt(pet.getFieldValue(fieldName).toString()) < Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    default -> {
//                        return filteredPets;
//                    }
//                }
//        } else {
//            for (Pet pet : petList)
//                switch (condition) {
//                    case ("=") -> {
//                        if (getId(pet) == Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case ("!=") -> {
//                        if (getId(pet) != Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case (">=") -> {
//                        if (getId(pet) >= Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case ("<=") -> {
//                        if (getId(pet) <= Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case (">") -> {
//                        if (Integer.parseInt(pet.getFieldValue(fieldName).toString()) > Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    case ("<") -> {
//                        if (Integer.parseInt(pet.getFieldValue(fieldName).toString()) < Integer.parseInt(value)) {
//                            filteredPets.add(pet);
//                        }
//                    }
//                    default -> {
//                        return filteredPets;
//                    }
//                }
//        }
//        return filteredPets;
//    }
    public List<Pet> filterPets( String fieldName, String value, String condition) {
        List<Pet> pets = selectAll();
        return pets.stream()
                .filter(pet -> {
                    Object fieldValue = pet.getFieldValue(fieldName);
                    if (fieldValue instanceof String) {
                        return evaluateStringCondition((String) fieldValue, value, condition);
                    } else if (fieldValue instanceof Integer) {
                        return evaluateIntegerCondition((Integer) fieldValue, Integer.parseInt(value), condition);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    private boolean evaluateStringCondition(String fieldValue, String value, String condition) {
        switch (condition) {
            case "=":
                return fieldValue.equals(value);
            case "!=":
                return !fieldValue.equals(value);
            default:
                return false;
        }
    }

    private boolean evaluateIntegerCondition(int fieldValue, int value, String condition) {
        switch (condition) {
            case "=":
                return fieldValue == value;
            case "!=":
                return fieldValue != value;
            case ">=":
                return fieldValue >= value;
            case "<=":
                return fieldValue <= value;
            case ">":
                return fieldValue > value;
            case "<":
                return fieldValue < value;
            default:
                return false;
        }
    }

}

