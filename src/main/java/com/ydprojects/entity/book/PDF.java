package com.ydprojects.entity.book;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.File;
import java.io.IOException;

@Entity
@Table(name = "book")
public class PDF extends BookImpl {
    @Transient
    private static final Logger LOG = LoggerFactory.getLogger(PDF.class);
    @Transient
    private PDDocument PDFDocument;
    @Transient
    private static final BookType BOOK_TYPE = BookType.PDF;

    private PDF(){
        super();
    }

    public PDF(String filePath, String bookContentsAsString, int wordCount, byte[] bookAsFile, String bookName) {
        super(filePath,BOOK_TYPE, bookContentsAsString, wordCount, bookAsFile, bookName);
    }

    public PDF(String bookName, String filePath) {
        super(bookName,filePath, BOOK_TYPE);

    }

    @Override
    public String convertBookToString() {
        loadPDF();
        try {
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            PDFTextStripper tStripper = new PDFTextStripper();
            return tStripper.getText(PDFDocument);
        } catch (IOException e) {
            LOG.info("{}",e);
        }
        return "failed to convert the pdf to string";
    }

    private void loadPDF() {
        try {
            PDFDocument = PDDocument.load(new File(getFilePath()));
        } catch (IOException e) {
            LOG.info("{}",e);
        }
    }
}
