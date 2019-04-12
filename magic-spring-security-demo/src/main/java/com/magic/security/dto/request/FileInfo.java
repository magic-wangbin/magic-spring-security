package com.magic.security.dto.request;

import java.io.Serializable;

public class FileInfo implements Serializable {
    private static final long serialVersionUID = 1032421668736295728L;

    private String filePath;

    public FileInfo(){}

    public FileInfo(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

