package com.traineeship.chemicalElements.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.traineeship.chemicalElements.entity.Element;
import com.traineeship.chemicalElements.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CSVFileService extends FileService {

    @Autowired
    public CSVFileService(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    private CSVReader createCSVReader(MultipartFile file, char separator, int skippedLines) throws IOException {

        Reader reader = new InputStreamReader(file.getInputStream());

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withIgnoreQuotations(true)
                .build();

        // closing stream
        return new CSVReaderBuilder(reader)
                .withSkipLines(skippedLines)
                .withCSVParser(parser)
                .build();
    }

    @Override
    protected String[] readHeader(MultipartFile file) throws IOException, CsvValidationException {

        CSVReader csvReader = createCSVReader(file, ';', 0);

        String[] res;
        // reading only the first line if exists
        return ((res = csvReader.readNext()) != null)? res : null;
    }

    // reading the content
    @Override
    protected List<Element> readFile(MultipartFile file) throws IOException, CsvValidationException {

        CSVReader csvReader = createCSVReader(file, ';', 1);

        List<Element> res = new ArrayList<>();
        String[] currentRow;

        while((currentRow = csvReader.readNext()) != null) {
            double[] parsedMeasurements = Arrays
                    // except from the first array element, which is name
                    .stream(currentRow, 1, currentRow.length)
                    // handling csv double numbers format
                    .map(measurement -> measurement.replace(',', '.'))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            res.add(new Element(currentRow[0], parsedMeasurements));
        }
        return res;
    }

    @Override
    public FileInfo uploadFile(MultipartFile uploadedFile) throws IOException, CsvException {

        fileInfo.setFileName((uploadedFile.getOriginalFilename()));
        fileInfo.setContentType(uploadedFile.getContentType());
        fileInfo.setFileEmpty(uploadedFile.isEmpty());
        fileInfo.setReadable(uploadedFile.getResource().isReadable());
        fileInfo.setFileExtension();

        fileInfo.setContent(readFile(uploadedFile)); // too much data to display
        fileInfo.setHeaders(readHeader(uploadedFile));

        return fileInfo; // add return body
    }
}
