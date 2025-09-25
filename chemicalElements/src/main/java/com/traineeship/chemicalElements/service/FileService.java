package com.traineeship.chemicalElements.service;

import com.opencsv.exceptions.CsvException;
import com.traineeship.chemicalElements.entity.Element;
import com.traineeship.chemicalElements.entity.FileInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    FileInfo uploadFile(MultipartFile uploadedFile) throws IOException, CsvException;
    List<Element> getContent();
    List<Element> getFilteredMeasurements(@PathVariable String elementName);
}
