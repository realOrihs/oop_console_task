package ru.oop.task.repository;

import ru.oop.task.model.domain.Database;
import ru.oop.task.model.domain.Pet;
import ru.oop.task.model.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepo extends BaseRepository<User>{
    private final List<String> existingFields = new ArrayList<>();
    public UserRepo(Database<User> userDb) {
        super(userDb);
        existingFields.add("id");
        existingFields.add("name");
        existingFields.add("last_name");
        existingFields.add("age");
        existingFields.add("pet_id");
    }

    public boolean checkIfFieldExist (String field){
        if (existingFields.contains(field)) {
            return true;
        } else {
            System.out.println("Поле " + field + " не существует.");
            return false;
        }
    }

//    public List<User> filterUsers (String fieldName, String value, String condition){
//        List<User> userList = selectAll();
//        List<User> filteredUsers = new ArrayList<>();
//            if (!fieldName.equals("id")) {
//                for (User user: userList)
//                    switch (condition) {
//                        case ("=") -> {
//                            if (user.getFieldValue(fieldName).equals(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case ("!=") -> {
//                            if (!user.getFieldValue(fieldName).equals(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case (">=") -> {
//                            if (Integer.parseInt(user.getFieldValue(fieldName).toString()) >= Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case ("<=") -> {
//                            if (Integer.parseInt(user.getFieldValue(fieldName).toString()) <= Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case (">") -> {
//                            if (Integer.parseInt(user.getFieldValue(fieldName).toString()) > Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case ("<") -> {
//                            if (Integer.parseInt(user.getFieldValue(fieldName).toString()) < Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        default -> {
//                            return filteredUsers;
//                        }
//                    }
//            }
//            else {
//                for (User user: userList)
//                    switch (condition){
//                        case ("=") -> {
//                            if (getId(user) == Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case ("!=") -> {
//                            if (getId(user) != Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case (">=") -> {
//                            if (getId(user) >= Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case ("<=") -> {
//                            if (getId(user) <= Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case (">") -> {
//                            if (Integer.parseInt(user.getFieldValue(fieldName).toString()) > Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        case ("<") -> {
//                            if (Integer.parseInt(user.getFieldValue(fieldName).toString()) < Integer.parseInt(value)) {
//                                filteredUsers.add(user);
//                            }
//                        }
//                        default -> {
//                            return filteredUsers;
//                        }
//                    }
//            }
//        return filteredUsers;
//    }
public List<User> filterUsers(String fieldName, String value, String condition) {
    List<User> users = selectAll();
    return users.stream()
            .filter(user -> {
                Object fieldValue = user.getFieldValue(fieldName);
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

    @Override
    public void update(User user, String fieldToUpdate, String valueToUpdate) {
        switch (fieldToUpdate) {
            case ("name") -> user.setName(valueToUpdate);
            case ("last_name") -> user.setLastName(valueToUpdate);
            case ("age") -> user.setAge(Integer.parseInt(valueToUpdate));
            case ("pet_id") -> user.setPetId(Integer.parseInt(valueToUpdate));
            default -> System.out.println("Поле " + fieldToUpdate + " не существует.");
        }
    }
}

