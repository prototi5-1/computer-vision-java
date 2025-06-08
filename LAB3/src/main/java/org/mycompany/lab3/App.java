package org.mycompany.lab3;

import org.mycompany.lab3.config.OpenCVLoader;

public class App {
    public static void main(String[] args) {
        // Загрузка OpenCV
        OpenCVLoader.load();

        // Можно добавить вызовы тестовых методов или запуск обработки
        System.out.println("OpenCV загружен успешно");
    }
}