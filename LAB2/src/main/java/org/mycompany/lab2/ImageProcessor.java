package org.mycompany.lab2;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.IntStream;

public class ImageProcessor {

    public ImageProcessor() {
        try {
            new ImageAPI(); // Инициализация ImageAPI для загрузки нативной библиотеки
        } catch (Exception e) {
            throw new RuntimeException("Failed to load native library", e);
        }
    }

    // Общий метод загрузки изображения в Mat по полному имени файла
    public Mat loadImage(String filePath) {
        Mat image = Imgcodecs.imread(filePath);
        if (image.empty()) {
            throw new IllegalArgumentException("Could not load image at: " + filePath);
        }
        return image;
    }

    // Обнуление указанного канала
    public void setChannelToZero(Mat image, int channel) {
        if (channel < 0 || channel >= image.channels()) {
            throw new IllegalArgumentException("Invalid channel number: " + channel);
        }

        int totalBytes = (int) (image.total() * image.elemSize());
        byte[] buffer = new byte[totalBytes];
        image.get(0, 0, buffer);

        IntStream.range(0, totalBytes)
                .filter(i -> i % image.channels() == channel)
                .forEach(i -> buffer[i] = 0);

        image.put(0, 0, buffer);
    }

    // Отображение изображения в JFrame
    public void showImage(Mat m) {
        int type = (m.channels() > 1) ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_BYTE_GRAY;
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b);
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);

        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth() + 50, image.getHeight() + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Закрытие только окна
    }

    // Сохранение изображения в файл
    public void saveImage(String filePath, Mat image) {
        if (!Imgcodecs.imwrite(filePath, image)) {
            throw new RuntimeException("Failed to save image to " + filePath);
        }
    }
}
