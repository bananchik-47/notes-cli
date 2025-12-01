package com.example;

import java.util.List;

public class App {

    public static void main(String[] args) {

        String cmd = null;
        String text = null;
        Integer id = null;

        // Разбираем аргументы
        for (String a : args) {
            if (a.startsWith("--cmd=")) cmd = a.substring(6);
            if (a.startsWith("--text=")) text = a.substring(7);
            if (a.startsWith("--id=")) id = Integer.parseInt(a.substring(5));
        }

        if (cmd == null) {
            System.out.println("Ошибка: укажите команду через --cmd");
            return;
        }

        NotesStore store = new NotesStore();

        switch (cmd) {

            case "add":
                if (text == null) {
                    System.out.println("Ошибка: для add нужен параметр --text");
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
                for (String[] n : notes) {
                    System.out.println(n[0] + ":" + n[1]);
                }
                break;

            case "rm":
                if (id == null) {
                    System.out.println("Ошибка: для rm нужен параметр --id");
                    return;
                }
                boolean ok = store.remove(id);
                if (!ok) {
                    System.out.println("Not found #" + id);
                }
                break;


            default:
                System.out.println("Неизвестная команда: " + cmd);
        }
    }
}
