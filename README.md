##Notes CLI

Консольная утилита для работы с текстовыми заметками.
Функционал реализуется в соответствии с техническим заданием.

##Возможности
##Версия v1.0.0

Добавление заметки (add)

Вывод всех заметок (list)

Постоянное хранение данных в файле data/notes.csv

CI-проверка компиляции Java-кода

##Версия v1.1.0

Удаление заметки по ID (rm)
или

Подсчёт количества заметок (count)
(в зависимости от выбранной дополнительной команды)

##Структура проекта

```notes-cli/
├─ src/
│  └─ com/example/
│     ├─ App.java
│     └─ NotesStore.java
├─ data/
│  └─ notes.csv
├─ Dockerfile
├─ .github/workflows/ci.yml
├─ .gitignore
└─ README.md
```

##Команды
Добавить заметку
```
java -cp src com.example.App --cmd=add --text="Купить хлеб"
```
Посмотреть список
```
java -cp src com.example.App --cmd=list
```
Удалить заметку
```
java -cp src com.example.App --cmd=rm --id=1
```
Посчитать заметки
```
java -cp src com.example.App --cmd=count
```
##Запуск в Docker
Сборка
```
docker build -t notes-cli:dev .
```
Добавление заметки
```
docker run --rm -v "$PWD/data:/app/data" notes-cli:dev --cmd=add --text="Test"
```
Просмотр списка
```
docker run --rm -v "$PWD/data:/app/data" notes-cli:dev --cmd=list
```
CI

Каждый push и pull request запускает компиляцию Java-файлов через GitHub Actions.
Файл workflow расположен по пути .github/workflows/ci.yml.

Релизы
v1.0.0

Реализованы команды add и list.

v1.1.0

Добавлена команда rm или count.
