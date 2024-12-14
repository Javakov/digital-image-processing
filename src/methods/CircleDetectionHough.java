package methods;

import helper.AbstractImageProcessor;
import org.opencv.core.Core;
import entity.Circle;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

public class CircleDetectionHough extends AbstractImageProcessor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Загружаем библиотеку OpenCV
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> detectCircle</li>
     *     <li><b>Описание:</b> Находит окружность на изображении с использованием преобразования Хафа для окружностей.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>bufferedImage</b> - исходное изображение типа BufferedImage, на котором нужно найти окружность.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Объект класса Circle с координатами центра и радиусом, если окружность найдена.</li>
     * </ul>
     */
    public Circle detectCircle(BufferedImage bufferedImage) {
        Mat image = bufferedImageToMat(bufferedImage);

        // Преобразуем изображение в оттенки серого
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Применяем гауссово размытие для улучшения качества
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(9, 9), 2, 2);

        // Для поиска окружности используем преобразование Хафа для окружностей
        Mat circles = new Mat();
        Imgproc.HoughCircles(blurredImage, circles, Imgproc.HOUGH_GRADIENT, 1,
                (double) blurredImage.rows() / 8, 200, 100, 0, 0);

        if (circles.cols() > 0) {
            // Извлекаем первую найденную окружность
            double[] circleData = circles.get(0, 0);
            int x = (int) circleData[0]; // Координаты центра окружности
            int y = (int) circleData[1];
            int radius = (int) circleData[2]; // Радиус окружности

            return new Circle(x, y, radius);
        }

        return null;
    }
}
