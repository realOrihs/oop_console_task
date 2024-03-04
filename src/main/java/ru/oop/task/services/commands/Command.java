package ru.oop.task.services.commands;

import java.util.Arrays;

public class Command {
    private final String commandName;
    private final String operators = "<=,>=,!=,=,<,>";
    public Integer length = 0;
    public String commandPointer;
    public String tableName;
    private String fieldName;
    private String fieldValue;
    private String fieldToUpdate;
    private String valueToUpdate;
    public String conditionOperand;
    public String conditionFieldName;
    public String conditionFieldValue;

    public Command(String [] splittedInput) {
        String [] words = splittedInput;
        this.commandName = words[0];
        this.length = words.length;
        switch (commandName.toUpperCase()) {
            case "SELECT" -> selectCommand(words);
            case "UPDATE" -> updateCommand(words);
            case "DELETE" -> deleteCommand(words);
            case "INSERT" -> insertCommand(words);
            default -> System.out.println("Команда не распознана, чето еще надо, поднажми");
        }
    }
    private String [] selectCommand(String[] words) {
        this.commandPointer = words[1];
        if (this.commandPointer.equals("*")) {
            this.tableName = words[3];
        } else {
            this.tableName = words[2];
            this.conditionOperand = Arrays.stream(operators.split(","))
                    .filter(words[4]::contains)
                    .findFirst()
                    .orElse("=");
            this.conditionFieldName = words[4].split(conditionOperand)[0];
            this.conditionFieldValue = words[4].split(conditionOperand)[1];
        }
        return words;
    }
    private String [] updateCommand(String[] words) {
        return words;
    }
    private String [] deleteCommand(String[] words) {
        return words;
    }
    private String [] insertCommand(String[] words) {
        return words;
    }


}
