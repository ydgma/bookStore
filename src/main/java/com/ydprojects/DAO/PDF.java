package com.ydprojects.DAO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class PDF extends BookType {
    private static final Logger LOG = LoggerFactory.getLogger(BookType.class);
    private String filePath;
    private PDDocument PDFDocument;

    public PDF(String filePath) {
        this.filePath = filePath;
        loadPDF();
        this.convertBookContentsToString();
    }

    @Override
    public void convertBookContentsToString() {
        try {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            PDFTextStripper tStripper = new PDFTextStripper();
            bookAsString = tStripper.getText(PDFDocument);

        } catch (IOException e) {
            LOG.info("{}",e);
        }
    }

    @Override
    public String getBookFilePath() {
        return filePath;
    }

    private void loadPDF() {
        try {
            PDFDocument = PDDocument.load(new File(filePath));
        } catch (IOException e) {
            LOG.info("{}",e);
        }
    }
}