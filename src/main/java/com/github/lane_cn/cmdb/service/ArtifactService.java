package com.github.lane_cn.cmdb.service;

import com.github.lane_cn.cmdb.domain.*;
import com.github.lane_cn.cmdb.dto.Entity;
import com.github.lane_cn.cmdb.dto.Type;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArtifactService {
    protected static final Logger LOG = LoggerFactory.getLogger(ArtifactService.class);
    private String storeDirectory;
    private File artifactoryPath = null;

    @PostConstruct
    public void init() {
        artifactoryPath = new File(new File(storeDirectory), "artifactory");
    }

    public List<Entity> getChildren(String name) {
        File path = StringUtils.isEmpty(name) ? artifactoryPath : new File(artifactoryPath, name);
        if (!path.exists()) {
            return null;
        }

        File[] files = path.listFiles((dir, n) -> !StringUtils.startsWith(n, "."));
        List<Entity> entities = new ArrayList<>();

        for (File file : files) {
            String baseName = file.getName();
            String type = null;

            if (file.isDirectory()) {
                type = Type.DIRECTORY;
            } else if (StringUtils.endsWithIgnoreCase(file.getName(), ".yaml")) {
                type = Type.ARTIFACT;
                baseName = StringUtils.left(baseName, baseName.length() - ".yaml".length());
            }

            if (StringUtils.isNotEmpty(type)) {
                Entity entity = new Entity();
                entity.setType(type);
                entity.setBaseName(baseName);
                if (StringUtils.isEmpty(name)) {
                    entity.setName(baseName);
                } else {
                    entity.setName(name + "/" + baseName);
                }
                entities.add(entity);
            }
        }

        return entities;
    }

    public Artifact getArtifact(String name) throws IOException {
        File file = new File(artifactoryPath, name + ".yaml");
        if (!file.exists()) {
            return null;
        }

        String content = FileUtils.readFileToString(file, "UTF-8");
        DumperOptions options = new DumperOptions();
        Yaml yaml = new Yaml(options);
        Artifact artifact = yaml.loadAs(content, Artifact.class);
        return artifact;
    }

    public List<Version> getVersions(String name, String version) throws IOException {
        Artifact artifact = getArtifact(name);
        if (artifact == null) {
            return null;
        }

        List<Version> vs = new ArrayList<>();
        for (Version v : artifact.getVersions()) {
            if (StringUtils.equals(v.getVersion(), version)) {
                vs.add(v);
            }
        }

        return vs.size() == 0 ? null : vs;
    }

    public Version getVersion(String name, String version, String classification) throws IOException {
        Artifact artifact = getArtifact(name);
        if (artifact == null) {
            return null;
        }

        for (Version v : artifact.getVersions()) {
            if (StringUtils.equals(v.getVersion(), version) && StringUtils.equals(v.getClassification(), classification)) {
                return v;
            }
        }

        return null;
    }

    private void test() {
        // 依赖 110
        DependOn p110 = new DependOn();
        p110.setName("infrastruction/jdk");
        p110.setVersion("1.8.121");
        List<DependOn> ps110 = new ArrayList<>();
        ps110.add(p110);

        // 依赖 100
        DependOn p100 = new DependOn();
        p100.setName("infrastruction/jdk");
        p100.setVersion("1.8.121");
        List<DependOn> ps100 = new ArrayList<>();
        ps100.add(p100);

        // 文档 110
        Document d110 = new Document();
        d110.setTitle("Product 1.1.0 版本发布说明");
        d110.setLink("http://release_server/product/1.1.0/release_note.html");
        List<Document> ds110 = new ArrayList<>();
        ds110.add(d110);

        // 文档 100
        Document d100 = new Document();
        d100.setTitle("Product 1.0.0 版本发布说明");
        d100.setLink("http://release_server/product/1.0.0/release_note.html");
        List<Document> ds100 = new ArrayList<>();
        ds100.add(d100);

        // 版本 110
        Version v110 = new Version();
        v110.setVersion("1.1.0");
        v110.setClassification("default");
        v110.setFileName("product-1.1.0.jar");
        v110.setDownloadUrl("http://release_server/product/1.1.0/product-1.1.0.jar");
        v110.setMd5sum("fe99a7baadd5604201c87d50b438d5b7");
        v110.setDocuments(ds110);
        v110.setDependsOn(ps110);

        // 版本 100
        Version v100 = new Version();
        v100.setVersion("1.0.0");
        v100.setClassification("default");
        v100.setFileName("product-1.0.0.jar");
        v100.setDownloadUrl("http://release_server/product/1.0.0/product-1.0.0.jar");
        v100.setMd5sum("6c4a33ff77b1306581157c581ed64f89");
        v100.setDocuments(ds100);
        v100.setDependsOn(ps100);

        List<Version> versions = new ArrayList<>();
        versions.add(v110);
        versions.add(v100);

        Artifact artifact = new Artifact();
        artifact.setBaseName("product");
        artifact.setName("business/product");
        artifact.setLicense("GPL");
        artifact.setProvider("zf");
        artifact.setPlatformIndependent(true);
        artifact.setVersions(versions);

        // 输出
        DumperOptions options = new DumperOptions();
        //options.setPrettyFlow(true);
        //options.setIndent(2);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);
        StringWriter sw = new StringWriter();
        yaml.dump(artifact, sw);
        LOG.info("{}", sw.toString());
    }
}
