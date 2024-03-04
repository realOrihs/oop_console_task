package ru.oop.task.services.commandprocessors;

import ru.oop.task.model.domain.Pet;
import ru.oop.task.model.domain.User;
import ru.oop.task.repository.PetRepo;
import ru.oop.task.repository.UserRepo;
import ru.oop.task.services.DatabaseService;


public class InsertProcessor {
    private UserRepo userRepo;
    private PetRepo petRepo;

    public InsertProcessor(UserRepo userRepo, PetRepo petRepo){
        this.userRepo = userRepo;
        this.petRepo = petRepo;
    }


    public String insert(String[] words) {
        if (DatabaseService.hasDb(words[2])) {
            String[] valueAndValues = words[3].split("\\(");
            String[] values = (valueAndValues[1].replace(")", "")).split(",");
//            String[] values = Arrays.stream(valueAndValues[1].replace(")","").split(","))
//                                    .map(s -> s.replaceAll("\\s", ""))
//                                    .toArray(String[]::new);
//            System.out.println(Arrays.toString(values));
            if (!words[1].equalsIgnoreCase("INTO") || !valueAndValues[0].equalsIgnoreCase("VALUES")
                    || values.length < 3 || values.length > 4) {
                return """
                        Неправильно оформлена команда INSERT.

                        Корректный ввод -> INSERT INTO {Название базы данных} VALUES({Значение}, {Значение}...)

                        """.formatted();
            }
              else
                if (words[2].equalsIgnoreCase("users"))
                {
                    String userName = values[0].replace("\"", "");
                    String userLastName =  values[1].replace("\"", "");
                    Integer userAge = Integer.parseInt(values[2]);
                    Integer userPetId = Integer.parseInt(values[3]);
                    User user = new User(userRepo.getCurrentId(),
                            userName,
                            userLastName,
                            userAge,
                            userPetId);
                    if (petRepo.petExists(userPetId)) {
                        userRepo.insert(user);
                        if (values.length == 4) {
                            user.setPetId(Integer.parseInt(values[3]));
                            return String.format("Inserted into %s with values (id=%d, name=%s, last_name=%s, age=%d, pet_id=%d",
                                    words[2], user.getId(), user.getName(), user.getLastName(), user.getAge(), user.getPetId());
                        } else {
                            return String.format("Inserted into %s with values (id=%d, name=%s, last_name=%s, age=%d",
                                    words[2], user.getId(), user.getName(), user.getLastName(), user.getAge());
                        }
                    }
                    else {
                        return String.format("Cannot insert a user into %s with nonexistent pet_id=%d",
                                words[2], user.getPetId());
                    }
                }
            else
            {
                String petName = values[0].replace("\"", "");
                String petBreed = values[1].replace("\"", "");
                Integer petAge = Integer.parseInt(values[2]);
                Pet pet = new Pet(petRepo.getCurrentId(),
                        petName,
                        petBreed,
                        petAge);
                petRepo.insert(pet);
                return String.format("Inserted into %s with values (id=%d, name=%s, breed=%s, age=%d)",
                        words[2], pet.getId(), pet.getName(), pet.getBreed(), pet.getAge());
            }
        }
        return String.format("Отсутствует база данных с таким названием : %s",words[2]);
    }
}
