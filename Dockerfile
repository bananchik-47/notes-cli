FROM eclipse-temurin:17-jdk

WORKDIR /app

# Копируем исходники
COPY src /app/src
COPY data /app/data

# JavaFX
RUN mkdir -p /opt/javafx
ADD https://download2.gluonhq.com/openjfx/21.0.2/openjfx-21.0.2_linux-x64_bin-sdk.zip /tmp/javafx.zip
RUN apt-get update && apt-get install -y unzip && \
    unzip /tmp/javafx.zip -d /opt/javafx && \
    rm /tmp/javafx.zip
ENV JAVAFX_HOME=/opt/javafx/javafx-sdk-21.0.2

# Создаём директорию для компиляции
RUN mkdir -p /app/out

# Компилируем проект
RUN find /app/src -name "*.java" > /tmp/sources.txt && \
    javac \
        --module-path $JAVAFX_HOME/lib \
        --add-modules javafx.controls,javafx.fxml \
        -d /app/out \
        @/tmp/sources.txt

# Volume для заметок
VOLUME ["/app/data"]

# Правильный CMD!
CMD ["java", "--module-path", "/opt/javafx/javafx-sdk-21.0.2/lib", "--add-modules", "javafx.controls,javafx.fxml", "-cp", "/app/out", "com.example.gui.Main"]
