package org.mycompany.lab2;

import javax.swing.JOptionPane;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ImageProcessorTest {

    private final String inputImagePath = "src/main/resources/input.jpg"; // путь к изображению
    private final String outputImagePath = "src/main/resources/output.jpg";

    static {
        try {
            new ImageAPI(); // OpenCV
        } catch (Exception e) {
            throw new RuntimeException("Failed to load native library", e);
        }
    }

    @Test
    public void testLoadImage() {
        ImageProcessor processor = new ImageProcessor();
        Mat image = processor.loadImage(inputImagePath);
        assertFalse(image.empty(), "Image should be loaded successfully");
        processor.showImage(image);
    }

    @Test
public void testSetChannelToZero() {
    ImageProcessor processor = new ImageProcessor();
    Mat image = processor.loadImage(inputImagePath);

    // исходное изображение
    processor.showImage(image);

    // Обнулим синий канал (OpenCV BGR: синий 0, зеленый 1, красный 2)
    processor.setChannelToZero(image, 0);

    // измененное изображение
    processor.showImage(image);

    // Сохранить результат
    processor.saveImage(outputImagePath, image);

    // Ожидание закрытия окон пользователем (диалог)
    JOptionPane.showMessageDialog(null, "Просмотрите изображения. Нажмите OK для завершения теста.");
    }
}