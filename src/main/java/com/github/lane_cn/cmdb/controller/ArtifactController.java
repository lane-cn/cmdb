package com.github.lane_cn.cmdb.controller;

import com.github.lane_cn.cmdb.domain.Artifact;
import com.github.lane_cn.cmdb.domain.Version;
import com.github.lane_cn.cmdb.dto.Entity;
import com.github.lane_cn.cmdb.service.ArtifactService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class ArtifactController {
    protected static final Logger LOG = LoggerFactory.getLogger(ArtifactController.class);

    @Autowired
    private ArtifactService artifactService;

    @GetMapping("/api/artifact/**")
    public ResponseEntity<Object> getArtifactObject(ServletWebRequest request) throws IOException {
        String uri = request.getRequest().getRequestURI();
        String name = StringUtils.substring(uri, "/api/artifact/".length());
        LOG.info("get artifact object: {}", name);

        List<Entity> entities = artifactService.getChildren(name);
        if (entities != null) {
            return new ResponseEntity<>(entities, HttpStatus.OK);
        }

        Artifact artifact = artifactService.getArtifact(name);
        if (artifact != null) {
            return new ResponseEntity<>(artifact, HttpStatus.OK);
        }

        String version = StringUtils.substringAfterLast(name, "/");
        name = StringUtils.substringBeforeLast(name, "/");
        List<Version> versions = artifactService.getVersions(name, version);
        if (versions != null) {
            return new ResponseEntity<>(versions, HttpStatus.OK);
        }

        String classification = version;
        version = StringUtils.substringAfterLast(name, "/");
        name = StringUtils.substringBeforeLast(name, "/");
        Version v = artifactService.getVersion(name, version, classification);
        if (v != null) {
            return new ResponseEntity<>(v, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/artifact/**")
    public String postArtifactObject(ServletWebRequest request, @RequestBody Object body) {
        String uri = request.getRequest().getRequestURI();
        String name = StringUtils.substring(uri, "/api/artifact/".length());
        LOG.info("post artifact object: {}", name);
        return null;
    }

    @PutMapping("/api/artifact/**")
    public void putArtifactObject(ServletWebRequest request, @RequestBody Object body) {
        String uri = request.getRequest().getRequestURI();
        String name = StringUtils.substring(uri, "/api/artifact/".length());
        LOG.info("put artifact object: {}", name);
    }

    @PatchMapping("/api/artifact/**")
    public void patchArtifactObject(ServletWebRequest request, @RequestBody Object body) {
        String uri = request.getRequest().getRequestURI();
        String name = StringUtils.substring(uri, "/api/artifact/".length());
        LOG.info("put artifact object: {}", name);
    }

    @DeleteMapping("/api/artifact/**")
    public String deleteArtifactObject(ServletWebRequest request) {
        String uri = request.getRequest().getRequestURI();
        String name = StringUtils.substring(uri, "/api/artifact/".length());
        LOG.info("delete artifact object: {}", name);
        return null;
    }

}
