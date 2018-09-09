package com.meraj.jackrabbit.controller;

import com.meraj.jackrabbit.Util.JackRabbitUtils;
import com.meraj.jackrabbit.model.FileResponse;
import com.meraj.jackrabbit.model.RabbitNode;
import com.meraj.jackrabbit.service.JackRabbitRepositoryBuilder;
import com.meraj.jackrabbit.service.JackRabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.net.URLConnection;
import java.util.List;

@RestController
@RequestMapping("/services")
public class RabbitController {
    Repository repo = null;
    Logger logger = LoggerFactory.getLogger(RabbitController.class);

    public RabbitController(JackRabbitService jackRabbitService) {
        this.jackRabbitService = jackRabbitService;
        repo = JackRabbitRepositoryBuilder.getRepo("localhost", 27017);
    }

    @Autowired
    JackRabbitService jackRabbitService;

    @RequestMapping(method = RequestMethod.POST, value = "/createRoot")
    public String createRoot() throws RepositoryException {
        Session session = JackRabbitUtils.getSession(repo);

        logger.error("createRoot called!");

        RabbitNode input = new RabbitNode("/", "emp", "", "");
        Node node = jackRabbitService.createFolderNode(session,input);

        String identifier = node.getIdentifier();
        JackRabbitUtils.cleanUp(session);
        return identifier;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createFolder")
    public String createFolderNode(@RequestBody RabbitNode input) throws RepositoryException {
        Session session = JackRabbitUtils.getSession(repo);

        logger.error("createFolderNode called!");
        logger.error("parentId: " + input.getParentId());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());
        logger.error("fileId: " + input.getFileId());

        Node node = jackRabbitService.createFolderNode(session, input);

        String identifier = node.getIdentifier();
        JackRabbitUtils.cleanUp(session);
        return identifier;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createFile")
    public String createNode(@RequestParam(value = "parent") String parent, @RequestParam(value = "file") MultipartFile file) throws RepositoryException {
        Session session = JackRabbitUtils.getSession(repo);
        RabbitNode input = new RabbitNode(parent, file.getOriginalFilename(), URLConnection.guessContentTypeFromName(file.getName()), "");

        logger.error("createNode called!");
        logger.error("parentId: " + input.getParentId());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());
        logger.error("fileId: " + input.getFileId());

        Node node = jackRabbitService.createNode(session, input, file);
        String identifier = node.getIdentifier();
        session.getNodeByIdentifier(input.getParentId());

        return identifier;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteFile")
    public boolean deleteNode(@RequestBody RabbitNode input) {
        Session session = JackRabbitUtils.getSession(repo);

        logger.error("deleteNode called!");
        logger.error("parentId: " + input.getParentId());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());
        logger.error("fileId: " + input.getFileId());

        boolean result = jackRabbitService.deleteNode(session, input);
        JackRabbitUtils.cleanUp(session);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getVersions")
    public List<String> getVersionHistory(@RequestBody RabbitNode input) {
        Session session = JackRabbitUtils.getSession(repo);

        logger.error("getVersionHistory called!");
        logger.error("parentId: " + input.getParentId());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());
        logger.error("fileId: " + input.getFileId());

        return jackRabbitService.getVersionHistory(session, input);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getFile/{versionId}")
    public FileResponse getNode(@PathVariable String versionId, @RequestBody RabbitNode input) {
        Session session = JackRabbitUtils.getSession(repo);
        FileResponse response = null;

        logger.error("getNode called!");
        logger.error("parentId: " + input.getParentId());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());
        logger.error("fileId: " + input.getFileId());

        response = jackRabbitService.getNode(session, versionId, input);
        return response;
    }
}

