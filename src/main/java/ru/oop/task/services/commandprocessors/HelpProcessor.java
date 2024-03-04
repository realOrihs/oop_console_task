package ru.oop.task.services.commandprocessors;

import java.util.HashMap;
import java.util.Map;

public class HelpProcessor {
//    static Map<String, String> commandDescriptions = new HashMap<>(); тут же данные в 1 строчку засунуть
    public static void help(String[] command) {
        if (!(command.length == 1 && command[0].equalsIgnoreCase("HELP"))) {
           return;
        }
        Map<String, String> commandDescriptions = new HashMap<>(); // -> вынести в статичное поле
        commandDescriptions.put("CREATE", "Create a new database");
        commandDescriptions.put("SELECT", "Select data from the database, Example: SELECT FROM {Название базы данных} WHERE({Поле},{Оператор сравнения} {Значение})");
        commandDescriptions.put("INSERT", "Insert data into the database, Example: INSERT INTO {Название базы данных} VALUES({Значение}, {Значение}...)");
        commandDescriptions.put("DELETE", "Delete data from the database");
        commandDescriptions.put("UPDATE", "Update data in the database");
        commandDescriptions.put("HELP", "Display help information");
        commandDescriptions.put("EXIT", "Exit the program");
        for (Map.Entry<String, String> entry : commandDescriptions.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}