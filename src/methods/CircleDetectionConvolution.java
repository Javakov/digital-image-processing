package methods;

import entity.Circle;
import helper.ImageProcessor;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

public class CircleDetectionConvolution extends ImageProcessor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Загружаем библиотеку OpenCV
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> Распознавание круга на изображении</li>
     *     <li><b>Описание:</b> Ищет круги на изображении с использованием метода свертки.</li>
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

        // Размытие изображения для улучшения совпадения
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(9, 9), 2, 2);

        // Загружаем шаблон круга (например, небольшой белый круг на черном фоне)
        Mat circleTemplate = createCircleTemplate();

        // Выполняем шаблонное совпадение (свертка)
        Mat result = new Mat();
        Imgproc.matchTemplate(blurredImage, circleTemplate, result, Imgproc.TM_CCOEFF);

        // Находим максимальное значение совпадения
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

        // Если максимальное совпадение больше порога, то это может быть круг
        if (mmr.maxVal > 0.8) {
            // Получаем координаты найденного круга
            Point matchLocation = mmr.maxLoc;
            int x = (int) matchLocation.x;
            int y = (int) matchLocation.y;
            int radius = circleTemplate.rows() / 2; // Радиус круга по шаблону

            return new Circle(x + radius, y + radius, radius);
        }

        return null;
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> createCircleTemplate</li>
     *     <li><b>Описание:</b> Создает шаблон круга заданного радиуса.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>radius</b> - радиус круга в пикселях.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Изображение шаблона круга.</li>
     * </ul>
     */
    private Mat createCircleTemplate() {
        // Создаем пустое изображение (черный фон)
        Mat template = Mat.zeros(new Size(2 * 30, 2 * 30), CvType.CV_8UC1);

        // Рисуем белый круг на черном фоне
        Imgproc.circle(template, new Point(30, 30), 30, new Scalar(255), -1);

        return template;
    }
}
