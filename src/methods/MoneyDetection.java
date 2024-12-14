package methods;

import helper.AbstractImageProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MoneyDetection extends AbstractImageProcessor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> countMoney</li>
     *     <li><b>Описание:</b> Подсчитывает количество денег на изображении (монет или банкнот).</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>bufferedImage</b> - исходное изображение типа BufferedImage с деньгами.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Количество объектов на изображении (монет или банкнот).</li>
     * </ul>
     */
    public int countMoney(BufferedImage bufferedImage) {
        Mat image = bufferedImageToMat(bufferedImage);

        // Преобразуем изображение в оттенки серого
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Применяем гауссово размытие для уменьшения шума
        Mat blurredImage = new Mat();
        Imgproc.GaussianBlur(grayImage, blurredImage, new Size(5, 5), 0);

        // Применяем пороговую обработку (бинаризация) для выделения объектов
        Mat binaryImage = new Mat();
        Imgproc.threshold(blurredImage, binaryImage, 100, 255, Imgproc.THRESH_BINARY_INV);

        // Находим контуры на бинаризованном изображении
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binaryImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Подсчитываем количество объектов
        int objectCount = 0;
        for (MatOfPoint contour : contours) {
            // Игнорируем маленькие шумы и слишком большие объекты
            if (Imgproc.contourArea(contour) > 500) {  // Порог площади для игнорирования шумов
                objectCount++;
            }
        }

        return objectCount;
    }
}
