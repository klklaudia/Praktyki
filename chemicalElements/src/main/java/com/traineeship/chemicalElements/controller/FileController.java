package com.traineeship.chemicalElements.controller;

import com.opencsv.exceptions.CsvException;
import com.traineeship.chemicalElements.entity.Element;
import com.traineeship.chemicalElements.entity.FileInfo;
import com.traineeship.chemicalElements.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // doesn't work without MediaType.MULTIPART_FORM_DATA_VALUE
    // uploading file
    // returns file information as json
    @PostMapping(path = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileInfo uploadFile(@RequestParam("file") MultipartFile uploadedFile) throws IOException, CsvException {
        return fileService.uploadFile(uploadedFile);
    }

    // just for testing -> data from and to db
    // getting data from variable
    @GetMapping(path = "/get-content")
    public List<Element> getContent() {
        return fileService.getContent();
    }

    // filtered elements based on user input
    @GetMapping(path = "/get-content/{element}")
    public List<Element> getFilteredMeasurements(@PathVariable String element) {
        return fileService.getFilteredMeasurements(element);
    }

}
