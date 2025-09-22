package com.traineeship.chemicalElements.controller;

import com.traineeship.chemicalElements.entity.FileInfo;
import com.traineeship.chemicalElements.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    public FileInfo uploadFile(@RequestParam("file") MultipartFile uploadedFile) throws IOException {

        // just files?
        return fileService.uploadFile(uploadedFile);

    }

    @GetMapping(path = "/get-content")
    public String readFile() {
        return fileService.readFile();
    }

}
