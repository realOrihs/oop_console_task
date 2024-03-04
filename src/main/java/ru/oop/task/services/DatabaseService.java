package ru.oop.task.services;

import ru.oop.task.model.domain.Database;
import ru.oop.task.model.domain.Pet;
import ru.oop.task.model.domain.User;

import java.util.HashMap;
import java.util.Map;

public class DatabaseService {
    //в ходе работы создавать инстансы репозиториев петов и юзеров
    //все классы подобные должны реализовать свои комманды через комманд процессор интерфейс

    private static final Map<String, Database<?>> dbmap = new HashMap<>();

    public static void init() {
        dbmap.put("users", new Database<User>());
        dbmap.put("pets", new Database<Pet>());
    }

    public static String create(String [] words) {
        dbmap.put(words[2], new Database<User>());
        return String.format("Table %s created with columns (id, first_name, second_name, age, pet_id)",words[2]);
    }

    public static Database<?> getDb (String dbName) {
        return dbmap.get(dbName);
    }

    //get dp по имени базы вернуть базу

    public static boolean hasDb(String name) {
        return dbmap.containsKey(name);
    }
}

