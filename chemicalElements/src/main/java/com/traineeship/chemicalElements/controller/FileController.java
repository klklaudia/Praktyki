package com.traineeship.chemicalElements.controller;

import com.opencsv.exceptions.CsvException;
import com.traineeship.chemicalElements.entity.FileInfo;
import com.traineeship.chemicalElements.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// controller for an upload

@RestController
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // doesn't work without MediaType.MULTIPART_FORM_DATA_VALUE
    // APPLICATION_XML
    // TEXT_XML

    @PostMapping(path = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public FileInfo uploadFile(@RequestParam("file") MultipartFile uploadedFile) throws IOException, CsvException {
        return fileService.uploadFile(uploadedFile);
    }

    // juz for testing -> data from db
    @GetMapping(path = "/get-content")
    public List<String[]> getContent() {
        return fileService.getContent();
    }

}
