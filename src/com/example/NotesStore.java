package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotesStore {

    // Файл заметок
    private final File file = new File("data/notes.csv");

    public NotesStore() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка: не удалось создать файл data/notes.csv");
        }
    }

    // Загрузить все заметки
    public List<String[]> loadAll() {
        List<String[]> notes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 2);
                if (parts.length == 2) {
                    notes.add(parts);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла заметок");
        }

        return notes;
    }

    // Добавить заметку
    public int add(String text) {
        List<String[]> notes = loadAll();

        int newId;

        if (notes.size() == 0) {
            newId = 1;
        } else {
            String[] last = notes.get(notes.size() - 1);
            newId = Integer.parseInt(last[0]) + 1;
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(newId + ";" + text + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка записи новой заметки");
        }

        return newId;
    }
}
