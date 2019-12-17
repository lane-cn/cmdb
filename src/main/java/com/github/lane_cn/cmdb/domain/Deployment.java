package com.github.lane_cn.cmdb.domain;

public class Deployment {
    private String hostSelector;
    private Resource resource;

    public String getHostSelector() {
        return hostSelector;
    }

    public void setHostSelector(String hostSelector) {
        this.hostSelector = hostSelector;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
