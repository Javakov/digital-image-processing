package methods;

import helper.AbstractImageProcessor;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 *     <li><b>Класс:</b> Определение угла поворота сканированного текста</li>
 * </ul>
 */
public class ImageRotation extends AbstractImageProcessor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> Определение угла поворота сканированного текста</li>
     *     <li><b>Описание:</b> Определяет угол поворота изображения с текстом с использованием преобразования Хафа для нахождения угла наклона контуров на изображении.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>bufferedImage</b> - исходное изображение типа BufferedImage.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Угол поворота изображения в градусах.</li>
     * </ul>
     */
    public double getRotationAngle(BufferedImage bufferedImage) {
        Mat image = bufferedImageToMat(bufferedImage);

        // Преобразуем изображение в оттенки серого
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Применяем бинаризацию
        Mat thresholdImage = new Mat();
        Imgproc.threshold(grayImage, thresholdImage, 0, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);

        // Находим контуры на изображении
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(thresholdImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Для каждого контура находим прямые с помощью преобразования Хафа
        Mat lines = new Mat();
        Imgproc.HoughLinesP(thresholdImage, lines, 1, Math.PI / 180, 100, 50, 10);

        // Вычисляем средний угол наклона
        double angle = 0;
        int count = 0;
        for (int i = 0; i < lines.rows(); i++) {
            double[] line = lines.get(i, 0);
            double x1 = line[0], y1 = line[1], x2 = line[2], y2 = line[3];
            double dx = x2 - x1;
            double dy = y2 - y1;
            double theta = Math.atan2(dy, dx) * 180 / Math.PI;
            angle += theta;
            count++;
        }

        // Средний угол наклона
        if (count > 0) {
            angle /= count;
        }

        return angle;
    }
}
