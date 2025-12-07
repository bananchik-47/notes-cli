package com.example.gui;

import com.example.NotesStore;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class NotesController {

    @FXML
    private TextField inputField;

    @FXML
    private ListView<String> listView;

    private final NotesStore store = new NotesStore();

    @FXML
    private void initialize() {

        // загрузка заметок
        loadNotes();

        // Enter = добавить заметку
        inputField.setOnAction(e -> handleAdd());

        // двойной клик = удалить заметку
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleRemove();
            }
        });

        listView.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    return;
                }

                setText(item);
                setStyle("-fx-font-size: 16px; -fx-padding: 6px 4px;");
            }
        });
    }

    private void loadNotes() {
        listView.getItems().clear();
        var rows = store.loadAll();

        for (String[] r : rows) {
            String formatted = r[0] + " | " + r[1];
            listView.getItems().add(formatted);
        }
    }

    @FXML
    private void handleAdd() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            store.add(text);
            loadNotes();
            inputField.clear();
        }
    }

    @FXML
    private void handleRemove() {
        int index = listView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            String line = listView.getItems().get(index);
            int id = Integer.parseInt(line.split("\\s\\|\\s")[0]);
            store.remove(id);
            loadNotes();
        }
    }

    @FXML
    private void handleCount() {
        int count = store.count();
        listView.getItems().add("Количество: " + count);
    }
}
