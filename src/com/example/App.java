package com.example;

import java.util.List;

public class App {

    public static void main(String[] args) {

        String cmd = null;
        String text = null;

        // Разбор аргументов командной строки
        for (String a : args) {
            if (a.startsWith("--cmd=")) cmd = a.substring(6);
            if (a.startsWith("--text=")) text = a.substring(7);
        }

        if (cmd == null) {
            System.out.println("Ошибка: укажите команду через --cmd");
            return;
        }

        NotesStore store = new NotesStore();

        switch (cmd) {

            case "add":
                if (text == null) {
                    System.out.println("Ошибка: для команды add нужен параметр --text");
                    return;
                }
                int newId = store.add(text);
                System.out.println("Добавлено. ID = " + newId);
                break;

            case "list":
                List<String[]> notes = store.loadAll();

                if (notes.size() == 0) {
                    System.out.println("(empty)");
                    return;
                }

                for (String[] row : notes) {
                    System.out.println(row[0] + ";" + row[1]);
                }
                break;

            default:
                System.out.println("Неизвестная команда");
                break;
        }
    }
}
