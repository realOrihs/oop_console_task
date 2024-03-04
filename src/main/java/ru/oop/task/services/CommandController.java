package ru.oop.task.services;

import ru.oop.task.model.domain.Database;
import ru.oop.task.model.domain.Pet;
import ru.oop.task.model.domain.User;
import ru.oop.task.repository.PetRepo;
import ru.oop.task.repository.UserRepo;
import ru.oop.task.services.commandprocessors.*;
import ru.oop.task.services.commands.Command;

public class CommandController {
    // private static Map<String, CommandProcessor> antiSwitchCase = new HashMap<>();
    // вместо вызова статичных методов - вызовы методов инстанса процессора нужного, где ключ команда а значение процессор
    // здесь создаем репозитории (инстансы) передаем их в процессоры, инстансы которых тоже здесь инициализируем
    private final PetRepo petRepo = new PetRepo((Database<Pet>) DatabaseService.getDb("pets"));
    private final UserRepo userRepo = new UserRepo((Database<User>) DatabaseService.getDb("users"));
    private final InsertProcessor insertProcessor = new InsertProcessor(userRepo, petRepo);
    private final SelectProcessor selectProcessor = new SelectProcessor(userRepo, petRepo);
    private final UpdateProcessor updateProcessor = new UpdateProcessor(userRepo, petRepo);
    private final DeleteProcessor deleteProcessor = new DeleteProcessor(userRepo, petRepo);


    public void handleCommand(String[] splittedInput) {
        Command command = new Command(splittedInput);
        switch (splittedInput[0].toUpperCase()) {
            case "CREATE" -> System.out.println(DatabaseService.create(splittedInput));
            case "SELECT" -> System.out.println(selectProcessor.select(command));
            case "UPDATE" -> System.out.println(updateProcessor.update(splittedInput));
            case "DELETE" -> System.out.println(deleteProcessor.delete(splittedInput));
            case "INSERT" -> System.out.println(insertProcessor.insert(splittedInput));
            case "EXIT" -> {System.out.println("Завершение работы"); System.exit(0);}
            case "HELP" -> HelpProcessor.help(splittedInput);
            default -> System.out.println("Команда не распознана, чето еще надо, поднажми");
        }


        //аналогично юзеры
    }
    // здесь должна происходить инициализация пустых баз Юзеров и Петов, записываем в dbmap

}


