package com.meraj.jackrabbit.service;

import com.meraj.jackrabbit.model.RabbitNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jcr.*;
import javax.jcr.version.Version;
import javax.jcr.version.VersionIterator;
import javax.jcr.version.VersionManager;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JackRabbitService {
    Logger logger = LoggerFactory.getLogger(JackRabbitService.class);

    public Node createFileNode(Session session, RabbitNode input) {
        Node node = null;
        File file = new File(input.getFileName());

        try {
            if (session.itemExists(input.getParentPath())) {
                logger.error("Parent node found!");
                Node parentNode = session.getNode(input.getParentPath());
                if (parentNode.hasNode(file.getName())) {
                    logger.error(file.getName() + " node already exists!");
                    return editNode(session, input);
                } else {
                    try {
                        node = parentNode.addNode(file.getName(), "nt:file");
                        node.addMixin("mix:versionable");

                        Node content = node.addNode("jcr:content", "nt:resource");

                        FileInputStream fileInputStream = new FileInputStream(file);
                        Binary binary = session.getValueFactory().createBinary(fileInputStream);

                        content.setProperty("jcr:data", binary);
                        content.setProperty("jcr:mimeType", input.getMimeType());

                        Date now = new Date();
                        now.toInstant().toString();
                        content.setProperty("jcr:lastModified", now.toInstant().toString());

                        fileInputStream.close();
                        session.save();

                        logger.error("File saved!");
                    } catch (Exception e) {
                        logger.error("Exception caught!");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception caught!");
            e.printStackTrace();
        }
        return node;
    }

    public boolean deleteNode(Session session, RabbitNode input) {
        try {
            if (session.itemExists(input.getFileName())) {
                Node node = session.getNode(input.getFileName());
                node.remove();
                session.save();
                return true;
            }
        } catch (Exception e) {
            logger.error("Exception caught!");
            e.printStackTrace();
        }

        return false;
    }

    public List<String> getVersionHistory(Session session, RabbitNode input) {
        List<String> versions = new ArrayList<>();
        try {
            VersionManager vm = session.getWorkspace().getVersionManager();

            String filePath = input.getFileName();
            if (session.itemExists(filePath)) {
                javax.jcr.version.VersionHistory versionHistory = vm.getVersionHistory(filePath);
                Version currentVersion = vm.getBaseVersion(filePath);
                logger.error("Current version: " + currentVersion.toString());

                VersionIterator versionIteratortr = versionHistory.getAllVersions();
                while (versionIteratortr.hasNext()) {
                    versions.add(((Version) versionIteratortr.next()).getIdentifier());
                }
            }
        } catch (Exception e) {
            logger.error("Exception caught!");
            e.printStackTrace();
        }
        return versions;
    }

    public Node editNode(Session session, RabbitNode input) {
        File file = new File(input.getFileName());
        Node returnNode = null;

        try {
            if (session.nodeExists(input.getParentPath()) && session.getNode(input.getParentPath()).hasNode(file.getName())) {
                VersionManager vm = session.getWorkspace().getVersionManager();

                Node node = session.getNode(input.getParentPath());
                Node fileNode = node.getNode(file.getName());
                vm.checkout(fileNode.getPath());

                Node content = fileNode.getNode("jcr:content");

                FileInputStream is = new FileInputStream(file);
                Binary binary = session.getValueFactory().createBinary(is);
                content.setProperty("jcr:data", binary);
                session.save();
                is.close();

                vm.checkin(fileNode.getPath());
                returnNode = fileNode;
            }
        } catch(Exception e) {
                logger.error("Exception caught");
                e.printStackTrace();
        }

        return returnNode;
    }

    public Node createFolderNode(Session session, RabbitNode input) {
        Node node = null;
        Node parentNode = null;

        try {
            parentNode = session.getNode(input.getParentPath());
            if (session.nodeExists(parentNode.getPath())) {
                if (!parentNode.hasNode(input.getFileName())) {
                    node = parentNode.addNode(input.getFileName(), "nt:folder");
                    session.save();
                    logger.error("Folder created!");
                }
            } else {
                logger.error("Node already exists!");
            }
        } catch (Exception e) {
            logger.error("Exception caught!");
            e.printStackTrace();
        }

        return node;
    }
}