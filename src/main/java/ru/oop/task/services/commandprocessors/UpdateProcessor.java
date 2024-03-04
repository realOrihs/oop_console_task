package ru.oop.task.services.commandprocessors;

import ru.oop.task.model.domain.Pet;
import ru.oop.task.model.domain.User;
import ru.oop.task.repository.PetRepo;
import ru.oop.task.repository.UserRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UpdateProcessor {
    private UserRepo userRepo;
    private PetRepo petRepo;
    public UpdateProcessor(UserRepo userRepo, PetRepo petRepo){
        this.userRepo = userRepo;
        this.petRepo = petRepo;
    }
    public String update(String[] words) {
        String commandSet = words[2];
        String updater = words[3];
        String commandWhere = words[4];
        String conditioner = words[5];
        if (!commandSet.equalsIgnoreCase("SET") || !updater.contains("=") || words.length != 6 || !commandWhere.equalsIgnoreCase("WHERE")
                || !(conditioner.contains("=") || conditioner.contains(">=") || conditioner.contains("<=") || conditioner.contains("!=")||
                conditioner.contains(">") || conditioner.contains("<"))) {
            return """
                    Неправильно оформлена команда UPDATE.
                                   
                    Корректный ввод -> UPDATE {Название базы данных} SET {Поле}={Значение} WHERE {Поле}{Тернарный оперантор}{Значение}
                                       
                    """;
        } else {
            return createOutput(words);
        }
    }

    private String createOutput(String[] words) {
        List<String> outputList = new ArrayList<>();
        String operators = "<=,>=,!=,=,<,>";
        String conditionOperand = Arrays.stream(operators.split(","))
                .filter(words[5]::contains)
                .findFirst()
                .orElse("=");
        String conditionFieldName = words[5].split(conditionOperand)[0];
        String conditionFieldValue = words[5].split(conditionOperand)[1];
        String fieldToUpdate = words[3].split("=")[0];
        String valueToUpdate = words[3].split("=")[1];
            if (petRepo.checkIfFieldExist(fieldToUpdate) && petRepo.checkIfFieldExist(conditionFieldName) && words[1].equalsIgnoreCase("pets")) {                // неверное условие вернуть хуйню
                outputList.add("""
                        |id|name|breed|age|
                        +--+----+-----+---+
                        """ );

                List<Pet> updatedPets = petRepo.filterPets(conditionFieldName, conditionFieldValue, conditionOperand);
                for (Pet pet : updatedPets) {
                    petRepo.update(pet, fieldToUpdate, valueToUpdate);
                    outputList.add("""
                            |%d|  %s|   %s| %d|
                            +--+----+-----+---+ \s
                            """.formatted(pet.getId(), pet.getName(), pet.getBreed(), pet.getAge()));
                }
            }
            else if (userRepo.checkIfFieldExist(fieldToUpdate) && userRepo.checkIfFieldExist(conditionFieldName)) {
                if (Objects.equals(fieldToUpdate, "pet_id")) {
                    if(petRepo.petExists(Integer.parseInt(valueToUpdate))) {
                        outputList.add("""
                                |id|first_name|last_name|age|pet_id|
                                +--+----------+-----------+---+------+
                                """);
                        List<User> updatedUsers = userRepo.filterUsers(conditionFieldName, conditionFieldValue, conditionOperand);
                        for (User user : updatedUsers) {
                            userRepo.update(user, fieldToUpdate, valueToUpdate);
                            outputList.add("""
                                    |%d|        %s|         %s| %d|    %s|
                                    +--+----------+-----------+---+------+  \s
                                    """.formatted(user.getId(), user.getName(), user.getLastName(), user.getAge(), user.getPetId().toString()));
                        }
                    }
                    else {
                        outputList.add("cannot update user with nonexistent petId");
                    }
                }
                else {
                    outputList.add("""
                                |id|first_name|last_name|age|pet_id|
                                +--+----------+-----------+---+------+
                                """);
                    List<User> updatedUsers = userRepo.filterUsers(conditionFieldName, conditionFieldValue, conditionOperand);
                    for (User user : updatedUsers) {
                        userRepo.update(user, fieldToUpdate, valueToUpdate);
                        outputList.add("""
                                    |%d|        %s|         %s| %d|    %s|
                                    +--+----------+-----------+---+------+  \s
                                    """.formatted(user.getId(), user.getName(), user.getLastName(), user.getAge(), user.getPetId() == null ? "" : user.getPetId().toString()));
                    }
                }
            }
        return String.join("", outputList);
    }

}

