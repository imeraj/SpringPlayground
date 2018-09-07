package com.meraj.jackrabbit.model;

public class RabbitNode {
    String parentPath;
    String filePath;
    String mimeType;

    public RabbitNode(String parentPath, String filePath, String mimeType) {
        this.parentPath = parentPath;
        this.filePath = filePath;
    }

    public RabbitNode() {
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
