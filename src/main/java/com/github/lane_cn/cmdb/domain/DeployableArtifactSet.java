package com.github.lane_cn.cmdb.domain;

import java.util.List;

public class DeployableArtifactSet {
    private DeployableArtifact artifact;
    private List<Configuration> configurations;
    private Deployment deployment;

    public DeployableArtifact getArtifact() {
        return artifact;
    }

    public void setArtifact(DeployableArtifact artifact) {
        this.artifact = artifact;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<Configuration> configurations) {
        this.configurations = configurations;
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }
}
