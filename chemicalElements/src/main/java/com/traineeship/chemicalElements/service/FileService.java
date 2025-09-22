package com.traineeship.chemicalElements.service;

import com.traineeship.chemicalElements.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class FileService {

    private FileInfo fileInfo;

    @Autowired
    public FileService(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public FileInfo uploadFile(MultipartFile uploadedFile) throws IOException {

        fileInfo.setFileName((uploadedFile.getOriginalFilename()));
        fileInfo.setContentType(uploadedFile.getContentType());
        fileInfo.setFileLength(uploadedFile.getSize());
        fileInfo.setFileEmpty(uploadedFile.isEmpty());
        fileInfo.setReadable(uploadedFile.getResource().isReadable());
        fileInfo.setFileData(uploadedFile.getBytes());

        return fileInfo;

    }

    public String readFile() {
        byte[] fileData = fileInfo.getFileData();
        // csv
        return new String(fileData, StandardCharsets.UTF_8);
    }

}
