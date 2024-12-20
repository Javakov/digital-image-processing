package methods;

import entity.Circle;
import helper.ImageProcessor;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 *     <li><b>Класс:</b> Распознавание круга на изображении</li>
 * </ul>
 */
public class CircleDetection extends ImageProcessor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Загружаем библиотеку OpenCV
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> Распознавание круга </li>
     *     <li><b>Описание:</b> Ищет круги на изображении с помощью контурного метода.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>bufferedImage</b> - исходное изображение типа BufferedImage.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Объект класса Circle, если круг найден, или null, если круг не найден.</li>
     * </ul>
     */
    public Circle detectCircle(BufferedImage bufferedImage) {
        Mat image = bufferedImageToMat(bufferedImage);

        // Преобразуем изображение в оттенки серого
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Размытие изображения для улучшения контуров
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(9, 9), 2, 2);

        // Найдем контуры
        Mat edges = new Mat();
        Imgproc.Canny(blurredImage, edges, 100, 200);

        // Найдем контуры
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Ищем круг среди контуров
        for (MatOfPoint contour : contours) {
            // Аппроксимация контура до окружности
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            RotatedRect minEllipse = Imgproc.fitEllipse(contour2f);

            // Проверяем, если найденная форма является окружностью
            double aspectRatio = minEllipse.size.width / minEllipse.size.height;
            if (Math.abs(aspectRatio - 1) < 0.2) { // проверка на круг (если соотношение сторон близко к 1)
                // Извлекаем координаты центра и радиус
                int x = (int) minEllipse.center.x;
                int y = (int) minEllipse.center.y;
                int radius = (int) (minEllipse.size.width / 2);

                return new Circle(x, y, radius);
            }
        }

        return null;
    }
}
