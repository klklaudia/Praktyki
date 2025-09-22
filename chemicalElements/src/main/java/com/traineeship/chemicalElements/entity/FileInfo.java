package com.traineeship.chemicalElements.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.File;

@Component // !!!
@Data // setters, getters, toString, hash...
public class FileInfo {

    // class types
    private String fileName;
    private long fileLength;
    private String contentType;
    private boolean isReadable;
    private boolean isFileEmpty;
    private byte[] fileData;

}
