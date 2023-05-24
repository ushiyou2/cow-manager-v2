package com.example.demo.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OCRService {

    public String extractTextFromImage(MultipartFile file) {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("src/main/resources/tessdata");
        try {
            File convertedFile = convertMultipartFileToFile(file);
            String result = tesseract.doOCR(convertedFile);
            convertedFile.delete(); // remember to remove temporary files
            return result;
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
}

