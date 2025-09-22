package com.traineeship.chemicalElements.controller;

import com.traineeship.chemicalElements.entity.FileInfo;
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


    private FileInfo fileInfo;

//    @Autowired
//    public FileController(FileInfo fileInfo) {
//        this.fileInfo = fileInfo;
//    }
//
//    public FileController() {}

    
    // doesn't work without MediaType.MULTIPART_FORM_DATA_VALUE
    // APPLICATION_XML
    // TEXT_XML

    @PostMapping(path = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public FileInfo uploadFile(@RequestParam("file") MultipartFile uploadedFile) throws IOException {

        FileInfo fileInfo = new FileInfo();

        fileInfo.setFileName((uploadedFile.getOriginalFilename()));
        fileInfo.setContentType(uploadedFile.getContentType());
        fileInfo.setFileLength(uploadedFile.getSize());
        fileInfo.setFileEmpty(uploadedFile.isEmpty());
        fileInfo.setReadable(uploadedFile.getResource().isReadable());
        fileInfo.setFileData(uploadedFile.getBytes());

        this.fileInfo = fileInfo;

        return this.fileInfo;
    }


    @GetMapping(path = "/get-content")
    public String readFile() {
        byte[] fileData = fileInfo.getFileData();
        return new String(fileData, StandardCharsets.UTF_8);
    }

//    @GetMapping("/read-file")
//    public void readFile() throws IOException {
//        LineIterator it = FileUtils.lineIterator(file2, "UTF-8");
//        try {
//            while (it.hasNext()) {
//                String line = it.nextLine();
//                System.out.println(line);
//            }
//        } finally {
//            LineIterator.closeQuietly(it);
//        }
//    }

}
