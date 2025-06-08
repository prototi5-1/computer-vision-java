package org.mycompany.lab6;

import org.mycompany.lab6.config.OpenCVLoader;
import org.mycompany.lab6.service.CannyEdgeDetector;
import org.mycompany.lab6.service.ImagePyramidService;
import org.mycompany.lab6.service.RectangleDetector;
import org.mycompany.lab6.model.RectangleDetectionResult;
import org.mycompany.lab6.util.ImageIOUtil;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class App {

    private App() {}

    public static void main(String[] args) {
        OpenCVLoader.load();

        CannyEdgeDetector canny = new CannyEdgeDetector();
        ImagePyramidService pyramidService = new ImagePyramidService();
        RectangleDetector rectangleDetector = new RectangleDetector();

        List<String> imageFiles = List.of("image1.jpg", "image2.jpg");

        imageFiles.stream().forEach(filename -> {
            Mat src = ImageIOUtil.loadImage(filename);

            double[][] thresholdsArr = {
                    {50, 150},
                    {100, 200},
                    {150, 250}
            };
            Stream<double[]> thresholdsStream = Stream.of(thresholdsArr);

            List<Mat> edgesList = canny.analyzeThresholds(src, thresholdsStream).toList();

            IntStream.range(0, edgesList.size())
                .forEach(idx -> {
                    Mat mat = edgesList.get(idx);
                    ImageIOUtil.saveImage(filename.replace(".jpg", "") + "_edges_" + idx + ".png", mat);
                    mat.release();
                });


            Mat down = pyramidService.downscale(src, 2);
            Mat up = pyramidService.upscale(down, 2);

            int w = up.cols() / 4;
            int h = up.rows() / 4;
            Rect roi = new Rect(up.cols()/2 - w/2, up.rows()/2 - h/2, w, h);
            pyramidService.getFragment(up, roi).ifPresent(fragment -> {
                ImageIOUtil.saveImage(filename.replace(".jpg", "") + "_fragment.png", fragment);
                fragment.release();
            });

            Mat diff = pyramidService.subtract(src, up);
            ImageIOUtil.saveImage(filename.replace(".jpg", "") + "_diff.png", diff);

            down.release();
            up.release();
            diff.release();

            if (!edgesList.isEmpty()) {
                Mat edgeForDetect = edgesList.get(0);
                RectangleDetectionResult result = rectangleDetector.detectRectangles(edgeForDetect, 100, 50, 10);
                System.out.printf("Image: %s, Rectangles %dx%d found: %d%n",
                        filename, result.getTargetWidth(), result.getTargetHeight(), result.getCount());
            }

            src.release();
        });
    }
}