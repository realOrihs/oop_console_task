package ru.oop.task;

import ru.oop.task.services.CommandController;
import ru.oop.task.services.DatabaseService;
import ru.oop.task.services.InputValidator;

import java.util.Scanner;

public class Main {
    //здесь должен происходить сначала вызов инпут валидатора, полученный распаршенный массив строк передаваться вызовом в Комманд контроллер;

    public static void main(String[] args){
        DatabaseService.init();
        CommandController commandController = new CommandController();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            String[] words = InputValidator.validate(command, scanner);

            if (words == null) {
                continue;
            }
            commandController.handleCommand(words);
        }
    }

}

