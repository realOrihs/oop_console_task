package ru.oop.task.services.commandprocessors;
import ru.oop.task.model.domain.Pet;
import ru.oop.task.model.domain.User;
import ru.oop.task.repository.PetRepo;
import ru.oop.task.repository.UserRepo;
import ru.oop.task.services.commands.Command;

import java.util.*;


public class SelectProcessor {
    private final UserRepo userRepo;
    private final PetRepo petRepo;
//    private final Map<String, BiConsumer<String[], List<String>>> actions = new HashMap<>();

    public SelectProcessor(UserRepo userRepo, PetRepo petRepo){
        this.userRepo = userRepo;
        this.petRepo = petRepo;
    }

    public String select(Command command) {
        List<String> outputList = new ArrayList<>();
        if (command.length < 4) {
            throw new IllegalArgumentException("""
                   Неправильно оформлена команда SELECT.

                   Корректный ввод -> SELECT INTO {Название базы данных} VALUES({Значение}, {Значение}...)

                 """);
        }
        if (command.commandPointer.equals("*")) {
            if (command.tableName.equalsIgnoreCase("pets")) {
                selectAllPets(outputList);
            }
            else {
                selectAllUsers(outputList);
            }
        }
        else if (command.tableName.equalsIgnoreCase("pets")) {
            selectPets(command, outputList);
        }
        else {
            selectUsers(command, outputList);
        }
        return String.join("", outputList);
    }

    private void selectAllPets(List<String> outputList) {
        outputList.add("""
                |id|name|breed|age|
                +--+----+-----+---+
                """);
        List<Pet> allPets = petRepo.selectAll();
        for (Pet pet : allPets) {
            addPetToOutputList(pet, outputList);
        }
    }

    private void selectAllUsers(List<String> outputList) {
        outputList.add("""
                |id|first_name|second_name|age|pet_id|
                +--+----------+-----------+---+------+
                """);
        List<User> allUsers = userRepo.selectAll();
        for (User user : allUsers) {
            addUserToOutputList(user, outputList);
        }
    }

    private void selectPets(Command command, List<String> outputList) {
        outputList.add("""
                |id|name|breed|age|
                +--+----+-----+---+
                """);

        List<Pet> selectedPets = petRepo.filterPets(command.conditionFieldName, command.conditionFieldValue, command.conditionOperand);
        for (Pet pet : selectedPets) {
            addPetToOutputList(pet, outputList);
        }
    }

    private void selectUsers(Command command, List<String> outputList) {
        outputList.add("""
                |id|first_name|second_name|age|pet_id|
                +--+----------+-----------+---+------+
                """);
        List<User> selectedUsers = userRepo.filterUsers(command.conditionFieldName, command.conditionFieldValue, command.conditionOperand);
        for (User user : selectedUsers) {
            addUserToOutputList(user, outputList);
        }
    }
    private void addPetToOutputList(Pet pet, List<String> outputList) {
        outputList.add("""
                |%d|  %s|   %s| %d|
                +--+----+-----+---+
                """.formatted(pet.getId(), pet.getName(), pet.getBreed(), pet.getAge()));
    }

    private void addUserToOutputList(User user, List<String> outputList) {
        outputList.add("""
                |%d|  %s|   %s| %d|
                +--+----+-----+---+
                """.formatted(user.getId(), user.getName(), user.getLastName(), user.getAge(), user.getPetId()));
    }
}
