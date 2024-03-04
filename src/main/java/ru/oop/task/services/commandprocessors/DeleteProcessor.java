package ru.oop.task.services.commandprocessors;

import ru.oop.task.model.domain.Pet;
import ru.oop.task.model.domain.User;
import ru.oop.task.repository.PetRepo;
import ru.oop.task.repository.UserRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteProcessor {
    private UserRepo userRepo;
    private PetRepo petRepo;
    public DeleteProcessor(UserRepo userRepo, PetRepo petRepo){
        this.userRepo = userRepo;
        this.petRepo = petRepo;
    }
    public String delete(String[] words) {
        if ((!words[1].equals("*") && !words[1].equalsIgnoreCase("FROM")
                && !words[3].equalsIgnoreCase("WHERE"))) {
            return """
                    Неправильно оформлена команда DELETE.
                                   
                    Корректный ввод -> DELETE FROM {Название базы данных} WHERE {Поле}{Тернарный оперантор}{Значение}
                                       
                    """;
        } else {
            return createOutput(words);
        }
    }

    private String createOutput(String[] words) {
        List<String> outputList = new ArrayList<>();
        if (words[1].equals("*") && words.length == 4 && words[2].equalsIgnoreCase("FROM")) {
            if (words[3].equalsIgnoreCase("pets")) {
                List<Pet> petList = petRepo.selectAll();
                for (Pet pet : petList) {
                    petRepo.delete(pet.getId());
                }
            } else {
                List<User> userList = userRepo.selectAll();
                for (User user : userList) {
                    userRepo.delete(user.getId());
                }
            }
            outputList = Collections.singletonList(("ALl entries deleted"));
            return String.join("", outputList);
        }
        else if (words[1].equalsIgnoreCase("FROM") && words.length == 5 && words[3].equalsIgnoreCase("WHERE")
                && (words[4].contains("=") || words[4].contains(">=") || words[4].contains("<=") || words[4].contains("!=")))
        {
            String condition = words[4].replaceAll("[a-zA-Z0-9\\s]", "");
            if (petRepo.checkIfFieldExist(words[4].split(condition)[0]) && words[2].equalsIgnoreCase("pets")) {                // неверное условие вернуть хуйню
                outputList.add("""
                        ENTRY
                        |id|name|breed|age|
                        +--+----+-----+---+
                        """ );

                List<Pet> selectedPets = petRepo.filterPets(words[4].split(condition)[0], words[4].split(condition)[1], condition);
                for (Pet pet : selectedPets) {
                    petRepo.delete(pet.getId());
                    outputList.add("""
                            |%d|  %s|   %s| %d|
                            +--+----+-----+---+ \s
                            """.formatted(pet.getId(), pet.getName(), pet.getBreed(), pet.getAge()));
                }
            }
            else {
                outputList.add("""
                        ENTRY
                        |id|first_name|second_name|age|pet_id|
                        +--+----------+-----------+---+------+
                        """);
                List<User> selectedUsers = userRepo.filterUsers(words[4].split(condition)[0], words[4].split(condition)[1], condition);
                for (User user : selectedUsers) {
                    userRepo.delete(user.getId());
                    outputList.add("""
                            |%d|        %s|         %s| %d|    %s|
                            +--+----------+-----------+---+------+  \s
                            """.formatted(user.getId(), user.getName(), user.getLastName(), user.getAge(), user.getPetId() == null ? "" : user.getPetId().toString()));
                }
            }
        }
        return String.join("", outputList).concat("DELETED");
    }
}

