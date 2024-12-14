package methods;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <ul>
 *     <li><b>Класс:</b> Пиксельные процессы</li>
 * </ul>
 */
public class PixelProcesses {

    /**
     * <ul>
     *     <li><b>Метод:</b> Просветление</li>
     *     <li><b>Описание:</b> Увеличивает яркость изображения, добавляя указанное значение к каждому каналу RGB.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>image</b> - исходное изображение.</li>
     *             <li><b>value</b> - значение, на которое увеличивается яркость (отрицательное для уменьшения).</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Новое изображение с увеличенной яркостью.</li>
     * </ul>
     */
    public BufferedImage brighten(BufferedImage image, int value) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int r = Math.min(255, color.getRed() + value); // Ограничиваем значение до 255
                int g = Math.min(255, color.getGreen() + value);
                int b = Math.min(255, color.getBlue() + value);
                result.setRGB(x, y, new Color(r, g, b).getRGB()); // Устанавливаем новый цвет пикселя
            }
        }

        return result;
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> Гистограмма</li>
     *     <li><b>Описание:</b> Создает изображение гистограммы, показывающее распределение яркостей (0-255) пикселей.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>image</b> - исходное изображение, для которого строится гистограмма.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Изображение с отрисованной гистограммой.</li>
     * </ul>
     */
    public BufferedImage drawHistogram(BufferedImage image) {
        int[] histogram = new int[256];

        // Вычисление гистограммы
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int gray = new Color(image.getRGB(x, y)).getRed();
                histogram[gray]++;
            }
        }

        // Нормализация данных для рисования
        int max = 0;
        for (int value : histogram) {
            max = Math.max(max, value);
        }

        int width = 512;
        int height = 400;
        BufferedImage histogramImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = histogramImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Отрисовка гистограммы
        g2d.setColor(Color.BLUE);
        int barWidth = width / histogram.length;
        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) ((histogram[i] / (double) max) * (height - 20)); // масштабируем по высоте
            g2d.fillRect(i * barWidth, height - barHeight, barWidth, barHeight);
        }

        g2d.dispose();
        return histogramImage;
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> Бинаризация</li>
     *     <li><b>Описание:</b> Преобразует изображение в черно-белое на основе порогового значения.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>image</b> - исходное изображение.</li>
     *             <li><b>threshold</b> - порог яркости (0-255), выше которого пиксели становятся белыми.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Бинаризированное изображение (черно-белое).</li>
     * </ul>
     */
    public BufferedImage binary(BufferedImage image, int threshold) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int gray = new Color(image.getRGB(x, y)).getRed(); // Извлекаем уровень серого
                int binaryColor = gray > threshold ? 255 : 0; // Сравниваем с порогом
                result.setRGB(x, y, new Color(binaryColor, binaryColor, binaryColor).getRGB()); // Устанавливаем черный или белый цвет
            }
        }

        return result;
    }
}
