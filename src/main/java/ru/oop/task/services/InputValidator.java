package ru.oop.task.services;

import java.util.Scanner;

public class InputValidator {
    public static String[] validate(String input, Scanner scanner) {
        String inputTrimmed = input.trim();
        if (input == null || inputTrimmed.isEmpty()) {
            System.out.println("Пустая команда! XD Введите-ка команду HELP или HeLp или help или hElP и так далее!");
            return null;
        } else if (isInputSticky(inputTrimmed)) {
            System.out.println("СтрокаНаписанаСлитноXD!!ВведитеЕеСПробелами!XD");
            return null;
        }
        return input.split("\\s+");
        }
    public static boolean isInputSticky (String inputTrimmed) {
           return (!inputTrimmed.contains(" ") && (!inputTrimmed.equalsIgnoreCase("exit") && !inputTrimmed.equalsIgnoreCase("help")));
        }
    }

