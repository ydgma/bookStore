package com.ydprojects.entity.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Entity
@Table(name = "book")
public class UTF8 extends BookImpl {
    @Transient
    private static final Logger LOG = LoggerFactory.getLogger(UTF8.class);
    @Transient
    private static final BookType BOOK_TYPE = BookType.UTF8;
    @Transient
    private String filepath;

    private UTF8(){
        super();
    }

    public UTF8(String filePath,String bookContentsAsString, int wordCount, byte[] bookAsFile, String bookName) {
        super(filePath,BOOK_TYPE, bookContentsAsString, wordCount, bookAsFile, bookName);
    }

    public UTF8(String bookName, String filePath) {
        super(bookName,filePath, BOOK_TYPE);
        this.filepath = filePath;

    }

    @Override
    public String convertBookToString () {
        String filePath = super.getFilePath();
        try {
            return String.join("", Files.readAllLines(Paths.get(filePath)));
        } catch (IOException e) {
            LOG.info("{}",e);
        }
        return "failed to convert book to String";
    }
}
