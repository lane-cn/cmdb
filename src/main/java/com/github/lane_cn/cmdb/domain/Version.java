package com.github.lane_cn.cmdb.domain;

import java.util.List;

public class Version {
    private String version;
    private String classification;
    private String fileName;
    private String downloadUrl;
    private String md5sum;
    private List<Document> documents;
    private List<DependOn> dependsOn;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<DependOn> getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(List<DependOn> dependsOn) {
        this.dependsOn = dependsOn;
    }
}
