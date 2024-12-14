package methods;

import helper.AbstractImageProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import entity.Line;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

public class LineDetectionHough extends AbstractImageProcessor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> detectLine</li>
     *     <li><b>Описание:</b> Находит одну прямую на изображении с использованием преобразования Хафа.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>bufferedImage</b> - исходное изображение типа BufferedImage, на котором нужно найти прямую.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Объект класса Line с углом и расстоянием от начала координат, если прямая найдена.</li>
     * </ul>
     */
    public Line detectLine(BufferedImage bufferedImage) {
        Mat image = bufferedImageToMat(bufferedImage);

        // Преобразуем изображение в оттенки серого
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Применяем гауссово размытие для улучшения качества
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(5, 5), 1);

        // Применяем детектор границ Canny
        Mat edges = new Mat();
        Imgproc.Canny(blurredImage, edges, 50, 150);

        // Используем преобразование Хафа для поиска прямых
        Mat lines = new Mat();
        Imgproc.HoughLines(edges, lines, 1, Math.PI / 180, 100);

        if (lines.rows() > 0) {
            // Извлекаем первую найденную прямую
            double[] lineData = lines.get(0, 0);
            double rho = lineData[0]; // Расстояние от начала координат
            double theta = lineData[1]; // Угол прямой

            return new Line(theta, rho);
        }

        return null;
    }
}
