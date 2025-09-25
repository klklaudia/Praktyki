package com.traineeship.chemicalElements.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component // detecting custom beans automatically
@Data
// idea: splitting file info into to classes: content and metadata
public class FileInfo {

    // class types
    private String fileName;
    private String contentType;
    private String fileExtension; // from a file name
    private boolean isReadable;
    private boolean isFileEmpty;
    private String[] headers; // from a file content
    private List<Element> content; // content without header

    public void setFileExtension() {
        String[] splitFileName = fileName.split("\\.");
        this.fileExtension = splitFileName[splitFileName.length - 1];
    }
}
