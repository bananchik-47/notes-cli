FROM eclipse-temurin:17-jdk

WORKDIR /app

# Копируем исходники
COPY src /app/src
COPY data /app/data

# Создаём директорию для компиляции
RUN mkdir -p /app/out

# Компилируем проект
RUN find /app/src -name "*.java" > /tmp/sources.txt && \
    javac -d /app/out @/tmp/sources.txt

# Объявляем volume для сохранения заметок
VOLUME ["/app/data"]

# Команда запуска
ENTRYPOINT ["java", "-cp", "/app/out", "com.example.App"]
