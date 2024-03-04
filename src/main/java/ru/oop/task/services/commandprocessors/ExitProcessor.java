package ru.oop.task.services.commandprocessors;

public class ExitProcessor {
    public static boolean isExitCommand(String[] command) {
        return command.length == 1 && command[0].equalsIgnoreCase("EXIT");
    }
}

