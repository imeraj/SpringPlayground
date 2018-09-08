package com.meraj.jackrabbit.model;

public class RabbitNode {
    String parentPath;
    String fileName;
    String mimeType;

    public RabbitNode(String parentPath, String fileName, String mimeType) {
        this.parentPath = parentPath;
        this.fileName = fileName;
    }

    public RabbitNode() {
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
