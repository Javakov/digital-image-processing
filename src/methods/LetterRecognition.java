package methods;

import helper.ImageProcessor;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LetterRecognition extends ImageProcessor {

    /**
     * <ul>
     *     <li><b>Метод:</b> recognizeLetters</li>
     *     <li><b>Описание:</b> Распознает текст с 31 изображения (по одной букве на изображение) в указанной директории.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>folderPath</b> - путь к папке, где хранятся изображения с буквами.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Список распознанных букв в виде строк.</li>
     * </ul>
     */
    public List<String> recognizeLetters(String folderPath) throws IOException, TesseractException {
        List<String> recognizedLetters = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            File imageFile = new File(folderPath + "letter_" + i + ".png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            String recognizedLetter = performOCR(bufferedImage);
            recognizedLetters.add(recognizedLetter);
        }

        return recognizedLetters;
    }

    /**
     * <ul>
     *     <li><b>Метод:</b> performOCR</li>
     *     <li><b>Описание:</b> Выполняет распознавание текста (буквы) с изображения с помощью Tesseract OCR.</li>
     *     <li><b>Аргументы:</b>
     *         <ul>
     *             <li><b>image</b> - изображение типа BufferedImage для распознавания.</li>
     *         </ul>
     *     </li>
     *     <li><b>Возвращает:</b> Распознанная строка с буквой.</li>
     * </ul>
     */
    private String performOCR(BufferedImage image) throws TesseractException {
        ITesseract instance = new Tesseract();

        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath());

        String result = instance.doOCR(image);

        return result.trim();
    }
}
