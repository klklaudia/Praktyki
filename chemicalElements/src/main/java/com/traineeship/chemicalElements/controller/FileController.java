package com.traineeship.chemicalElements.controller;

import com.opencsv.exceptions.CsvException;
import com.traineeship.chemicalElements.entity.Element;
import com.traineeship.chemicalElements.entity.FileInfo;
import com.traineeship.chemicalElements.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// exceptions: file (empty, unreadable due to extension/permission),
// elements: not existing, no measurements
// sensitive-case

@Tag(name = "File Rest API Endpoints",
        description = "Operations on files - uploading and getting optionally filtered data. " +
                "Files contains measurements related to chemical elements.")
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
    @Operation(summary = "Upload a file")
    @PostMapping(path = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileInfo uploadFile(@Parameter(description = "a file to upload") @RequestParam("file") MultipartFile uploadedFile) throws IOException, CsvException {
        return fileService.uploadFile(uploadedFile);
    }

    // just for testing -> data from and to db
    // getting data from variable
    @Operation(summary = "Retrieve all measurements")
    @GetMapping(path = "/get-content")
    public List<Element> getContent() {
        return fileService.getContent();
    }

    // filtered elements based on user input
    @Operation(summary = "Retrieve measurements for chosen chemical element")
    @GetMapping(path = "/get-content/{element}")
    public List<Element> getFilteredMeasurements(@Parameter(description = "a chemical element") @PathVariable String element) {
        return fileService.getFilteredMeasurements(element);
    }

}
