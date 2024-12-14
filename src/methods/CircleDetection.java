package methods;

import entity.Circle;
import helper.AbstractImageProcessor;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

/**
 * <ul>
 *     <li><b>Класс:</b> Распознавание круга на изображении</li>
 * </ul>
 */
public class CircleDetection extends AbstractImageProcessor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Загружаем библиотеку OpenCV
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> Распознавание круга на изображении</li>
     *     <li><b>Описание:</b> Распознает круги на изображении с использованием алгоритма Хафа для кругов.</li>
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

        // Размытие изображения для улучшения обнаружения
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(9, 9), 2, 2);

        // Для поиска кругов используем преобразование Хафа для кругов
        Mat circles = new Mat();
        Imgproc.HoughCircles(blurredImage, circles, Imgproc.HOUGH_GRADIENT, 1,
                (double) blurredImage.rows() / 8, 200, 100, 0, 0);

        if (circles.cols() > 0) {
            // Извлекаем первый найденный круг
            double[] circleData = circles.get(0, 0);
            int x = (int) circleData[0];
            int y = (int) circleData[1];
            int radius = (int) circleData[2];

            return new Circle(x, y, radius);
        }

        return null;
    }
}
