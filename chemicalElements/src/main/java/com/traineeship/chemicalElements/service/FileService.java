package com.traineeship.chemicalElements.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.traineeship.chemicalElements.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;


@Service
public class FileService {

    private FileInfo fileInfo;

    @Autowired
    public FileService(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    // separator as default parameter
    public FileInfo uploadFile(MultipartFile uploadedFile) throws IOException, CsvException {

        fileInfo.setFileName((uploadedFile.getOriginalFilename()));
        fileInfo.setContentType(uploadedFile.getContentType());
        fileInfo.setFileEmpty(uploadedFile.isEmpty());
        fileInfo.setReadable(uploadedFile.getResource().isReadable());
        List<String[]> wholeFileContent = readFile(uploadedFile);
        fileInfo.setContent(wholeFileContent.subList(1, wholeFileContent.size())); // too much to display
        fileInfo.setHeaders(List.of(wholeFileContent.getFirst()));
        fileInfo.setFileExtension();
        return fileInfo; // add return body
    }

    // reading a csv file
    private List<String[]> readFile(MultipartFile file) throws IOException, CsvException {
        Reader reader = new InputStreamReader(file.getInputStream());

        char separator = ';';
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(true)
                .build();

        int skippedLines = 0;
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(skippedLines)
                .withCSVParser(parser)
                .build();

        return csvReader.readAll();
    }

    // -> change to db
    public List<String[]> getContent() {
        return fileInfo.getContent();
    }

}
