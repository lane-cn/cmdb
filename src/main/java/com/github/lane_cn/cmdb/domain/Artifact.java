package com.github.lane_cn.cmdb.domain;

import java.util.List;

public class Artifact {
    private String baseName;
    private String name;
    private String provider;
    private String license;
    private boolean platformIndependent;
    private List<Version> versions;

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public boolean isPlatformIndependent() {
        return platformIndependent;
    }

    public void setPlatformIndependent(boolean platformIndependent) {
        this.platformIndependent = platformIndependent;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }
}
