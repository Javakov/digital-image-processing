import entity.Circle;
import entity.Line;
import methods.*;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, TesseractException {
        BufferedImage image = ImageIO.read(new File("images_original/original_img.png"));
        BufferedImage text = ImageIO.read(new File("images_original/3_angle_text_rotate.png"));
        BufferedImage circle = ImageIO.read(new File("images_original/circle.png"));
        BufferedImage money = ImageIO.read(new File("images_original/money.png"));
        BufferedImage line = ImageIO.read(new File("images_original/line.png"));

        PixelProcesses pixelProcesses = new PixelProcesses();
        SpatialProcesses spatialProcesses = new SpatialProcesses();
        GeometricProcesses geometricProcesses = new GeometricProcesses();
        ImageRotation imageRotation = new ImageRotation();
        CircleDetection circleDetection = new CircleDetection();
        LetterRecognition letterRecognition = new LetterRecognition();
        CircleDetectionConvolution circleDetectionConvolution = new CircleDetectionConvolution();
        MoneyDetection moneyDetection = new MoneyDetection();
        CircleDetectionHough circleDetectionHough = new CircleDetectionHough();
        LineDetectionHough lineDetectionHough = new LineDetectionHough();

        BufferedImage brightenedImage = pixelProcesses.brighten(image, 50);
        System.out.println("Осветление изображения завершено.");
        ImageIO.write(brightenedImage, "png", new File("images_result/brighten_img.png"));

        BufferedImage histogramImage = pixelProcesses.drawHistogram(image);
        System.out.println("Построение гистограммы завершено.");
        ImageIO.write(histogramImage, "png", new File("images_result/histogram_img.png"));

        BufferedImage binaryImage = pixelProcesses.binary(image, 90);
        System.out.println("Бинаризация изображения завершена.");
        ImageIO.write(binaryImage, "png", new File("images_result/binary_img.png"));

        BufferedImage meanFilterImage = spatialProcesses.applyMeanFilter(image);
        System.out.println("Применение фильтра усреднения завершено.");
        ImageIO.write(meanFilterImage, "png", new File("images_result/mean_filter_img.png"));

        BufferedImage scaleImage = geometricProcesses.scale(image, 1.5, 1.5);
        System.out.println("Масштабирование изображения завершено.");
        ImageIO.write(scaleImage, "png", new File("images_result/scale_img.png"));

        BufferedImage rotateImage = geometricProcesses.rotate(image, 10);
        System.out.println("Поворот изображения завершён.");
        ImageIO.write(rotateImage, "png", new File("images_result/rotate_img.png"));

        double rotationAngle = imageRotation.getRotationAngle(text);
        System.out.println("Угол поворота изображения: " + rotationAngle);

        Circle circleDetect = circleDetection.detectCircle(circle);
        if (circleDetect != null) {
            System.out.println("Круг найден: " +
                    "X = " + circleDetect.x + ", " +
                    "Y = " + circleDetect.y + ", " +
                    "Радиус = " + circleDetect.radius);
        } else {
            System.out.println("Круг не найден.");
        }

        List<String> recognizedLetters = letterRecognition.recognizeLetters("images_letters/");
        for (String letter : recognizedLetters) {
            System.out.println("Оцифрованная буква: " + letter);
        }

        Circle circleDetectConvolution = circleDetectionConvolution.detectCircle(circle);
        if (circleDetectConvolution != null) {
            System.out.println("Круг методом свертки найден: " +
                    "X = " + circleDetectConvolution.x + ", " +
                    "Y = " + circleDetectConvolution.y + ", " +
                    "Радиус = " + circleDetectConvolution.radius);
        } else {
            System.out.println("Круг методом свертки не найден.");
        }

        int countMoney = moneyDetection.countMoney(money);
        System.out.println("Количество денег: " + countMoney);

        Circle circleHough = circleDetectionHough.detectCircle(circle);
        if (circleHough != null) {
            System.out.println("Круг методом Хафа найден: " +
                    "X = " + circleHough.x + ", " +
                    "Y = " + circleHough.y + ", " +
                    "Радиус = " + circleHough.radius);
        } else {
            System.out.println("Круг методом Хафа не найден.");
        }

        Line lineHough = lineDetectionHough.detectLine(line);
        if (lineHough != null) {
            System.out.println("Линия методом Хафа найдена: " +
                    "угол = " + lineHough.angle + ", " +
                    "расстояние = " + lineHough.distance);
        } else {
            System.out.println("Линия методом Хафа не найдена.");
        }
    }
}