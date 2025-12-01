package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotesStore {

    // Файл с заметками
    private final File file = new File("data/notes.csv");

    public NotesStore() {
        try {
            // Если файла нет — создаём
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Не получилось создать файл заметок");
        }
    }

    // Загрузка всех заметок
    public List<String[]> loadAll() {
        List<String[]> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 2);

                if (parts.length == 2) {
                    list.add(parts);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл");
        }

        return list;
    }

    // Добавить новую заметку
    public int add(String text) {
        List<String[]> rows = loadAll();

        int newId;

        if (rows.size() == 0) {
            newId = 1;
        } else {
            // Берём ID последней записи + 1
            String[] last = rows.get(rows.size() - 1);
            newId = Integer.parseInt(last[0]) + 1;
        }

        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(newId + ";" + text + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Не удалось записать заметку");
        }

        return newId;
    }

    // Посчитать количество
    public int count() {
        return loadAll().size();
    }

    // Удалить по ID
    public boolean remove(int id) {
        List<String[]> rows = loadAll();
        boolean found = false;

        // Удаляем запись
        List<String[]> newList = new ArrayList<>();

        for (String[] r : rows) {
            int currentId = Integer.parseInt(r[0]);

            if (currentId == id) {
                found = true;
            } else {
                newList.add(r);
            }
        }

        // Если не нашли — значит false
        if (!found) return false;

        // Сохраняем обновлённый список
        save(newList);
        return true;
    }

    // Сохранить полностью новый список
    private void save(List<String[]> rows) {
        try (FileWriter writer = new FileWriter(file, false)) {
            for (String[] r : rows) {
                writer.write(r[0] + ";" + r[1] + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения данных");
        }
    }
}
