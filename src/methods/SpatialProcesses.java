package methods;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * <ul>
 *     <li><b>Класс:</b> Пространственные процессы</li>
 * </ul>
 */
public class SpatialProcesses {

    /**
     * <ul>
     *     <li><b>Метод:</b> Средний фильтр</li>
     *     <li><b>Описание:</b> Применяет средний фильтр для сглаживания изображения.
     *     Используется 3x3 ядро, где каждый элемент имеет равный вес (1/9).</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>image</b> - изображение, к которому применяется фильтр.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Изображение после применения среднего фильтра.</li>
     * </ul>
     */
    public BufferedImage applyMeanFilter(BufferedImage image) {
        float[] kernel = {
                1f/9, 1f/9, 1f/9,
                1f/9, 1f/9, 1f/9,
                1f/9, 1f/9, 1f/9
        };

        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, kernel));

        return op.filter(image, null);
    }
}
