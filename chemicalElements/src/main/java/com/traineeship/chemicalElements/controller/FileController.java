package com.traineeship.chemicalElements.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// controller for an upload

@RestController
public class FileController {

    @PostMapping(path = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(MultipartFile file) {

    }

}
