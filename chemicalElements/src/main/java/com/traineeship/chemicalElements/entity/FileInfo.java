package com.traineeship.chemicalElements.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // !!!
@Data // setters, getters, toString, hash...
// idea: splitting file info into to classes: content and metadata
public class FileInfo {

    // class types
    private String fileName;
    private String contentType;
    private String fileExtension; // from file name
    private boolean isReadable;
    private boolean isFileEmpty;
    private List<String> headers; // from file content
    private List<String[]> content; // content without header

    public void setFileExtension() {
        String[] splitFileName = fileName.split("\\.");
        this.fileExtension = splitFileName[splitFileName.length - 1];
    }
}
