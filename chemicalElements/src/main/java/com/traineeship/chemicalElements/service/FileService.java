package com.traineeship.chemicalElements.service;

import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.traineeship.chemicalElements.entity.Element;
import com.traineeship.chemicalElements.entity.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public abstract class FileService {

    protected FileInfo fileInfo;

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

    protected abstract List<Element> readFile(MultipartFile uploadedFile) throws IOException, CsvValidationException;
    protected abstract String[] readHeader(MultipartFile uploadedFile) throws IOException, CsvValidationException;

    // -> change to db
    public final List<Element> getContent() {
        return fileInfo.getContent();
    }

    public final List<Element> getFilteredMeasurements(@PathVariable String elementName) {

        return fileInfo.getContent().stream()
                // chemical element name must case-sensitive (CO =/= Co) (
                .filter(element -> element.getName().equals(elementName))
                .toList();
    }
}
