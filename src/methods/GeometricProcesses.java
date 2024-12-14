package methods;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * <ul>
 *     <li><b>Класс:</b> Геометрические процессы</li>
 * </ul>
 */
public class GeometricProcesses {

    /**
     * <ul>
     *     <li><b>Метод:</b> Масштабирование</li>
     *     <li><b>Описание:</b> Изменяет размеры изображения в соответствии с указанными коэффициентами масштабирования.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>image</b> - исходное изображение.</li>
     *             <li><b>scaleX</b> - коэффициент масштабирования по оси X.</li>
     *             <li><b>scaleY</b> - коэффициент масштабирования по оси Y.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Изображение с новыми размерами.</li>
     * </ul>
     */
    public BufferedImage scale(BufferedImage image, double scaleX, double scaleY) {
        int newWidth = (int) (image.getWidth() * scaleX);
        int newHeight = (int) (image.getHeight() * scaleY);

        BufferedImage result = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g2d = result.createGraphics();

        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        return result;
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> Поворот</li>
     *     <li><b>Описание:</b> Поворачивает изображение на указанный угол в градусах. Новый размер изображения учитывает полный охват после поворота.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>image</b> - исходное изображение.</li>
     *             <li><b>angle</b> - угол поворота в градусах (по часовой стрелке).</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Повернутое изображение с учетом нового размера.</li>
     * </ul>
     */
    public BufferedImage rotate(BufferedImage image, double angle) {
        double radians = Math.toRadians(angle);
        int newWidth = (int) Math.abs(image.getWidth() * Math.cos(radians) + image.getHeight() * Math.sin(radians));
        int newHeight = (int) Math.abs(image.getHeight() * Math.cos(radians) + image.getWidth() * Math.sin(radians));

        BufferedImage result = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g2d = result.createGraphics();

        g2d.rotate(radians, (double) newWidth / 2, (double) newHeight / 2);
        g2d.drawImage(image, (newWidth - image.getWidth()) / 2, (newHeight - image.getHeight()) / 2, null);
        g2d.dispose();

        return result;
    }
}
