package helper;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public class ImageProcessor {

    /**
     * <ul>
     *     <li><b>Метод:</b> bufferedImageToMat</li>
     *     <li><b>Описание:</b> Преобразует объект типа BufferedImage в объект типа Mat, который используется в OpenCV для обработки изображений.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>bufferedImage</b> - изображение типа BufferedImage.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Матрица (Mat), представляющая изображение.</li>
     * </ul>
     */
    protected static Mat bufferedImageToMat(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        Mat mat = new Mat(height, width, CvType.CV_8UC3);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = bufferedImage.getRGB(x, y);
                byte b = (byte) ((color) & 0xFF);
                byte g = (byte) ((color >> 8) & 0xFF);
                byte r = (byte) ((color >> 16) & 0xFF);
                mat.put(y, x, new byte[]{b, g, r});
            }
        }

        return mat;
    }
}
