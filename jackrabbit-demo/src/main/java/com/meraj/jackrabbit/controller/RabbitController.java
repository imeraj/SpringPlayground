package com.meraj.jackrabbit.controller;

import com.meraj.jackrabbit.Util.JackRabbitUtils;
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

        RabbitNode input = new RabbitNode("/", "emp", "");
        Node node = jackRabbitService.createFolderNode(session,input);

        if (node == null)
            return null;
        else
            return node.getIdentifier();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createFolder")
    public String createFolderNode(@RequestBody RabbitNode input) throws RepositoryException {
        Session session = JackRabbitUtils.getSession(repo);

        logger.error("createFolderNode called!");
        logger.error("parentId: " + input.getParentPath());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());

        Node node = jackRabbitService.createFolderNode(session, input);

        return node.getIdentifier();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createFile")
    public String createNode(@RequestParam(value = "parent") String parent, @RequestParam(value = "file") MultipartFile file) throws RepositoryException {
        Session session = JackRabbitUtils.getSession(repo);
        RabbitNode input = new RabbitNode(parent, file.getOriginalFilename(), URLConnection.guessContentTypeFromName(file.getName()));

        logger.error("createNode called!");
        logger.error("parentId: " + input.getParentPath());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());

        Node node = jackRabbitService.createNode(session, input, file);
        // JackRabbitUtils.cleanUp(session);

        return node.getIdentifier();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteFile")
    public boolean deleteNode(@RequestBody RabbitNode input) {
        Session session = JackRabbitUtils.getSession(repo);

        logger.error("deleteNode called!");
        logger.error("parentId: " + input.getParentPath());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());

        return jackRabbitService.deleteNode(session, input);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getVersions")
    public List<String> getVersionHistory(@RequestBody RabbitNode input) {
        Session session = JackRabbitUtils.getSession(repo);

        logger.error("getVersionHistory called!");
        logger.error("parentId: " + input.getParentPath());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());

        return jackRabbitService.getVersionHistory(session, input);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadNewVersion")
    public String editNode(@RequestParam(value = "parent") String parent, @RequestParam(value = "file") MultipartFile file) {
        Session session = JackRabbitUtils.getSession(repo);
        String identifier = null;

        RabbitNode input = new RabbitNode(parent, file.getOriginalFilename(), URLConnection.guessContentTypeFromName(file.getName()));

        logger.error("editNode called!");
        logger.error("parentId: " + input.getParentPath());
        logger.error("filePath: " + input.getFileName());
        logger.error("mimeType: " + input.getMimeType());

        try {
             identifier = jackRabbitService.editNode(session, input, file).getIdentifier();
        } catch (Exception e) {
            logger.error("Caught exception");
            e.printStackTrace();
        }

        return identifier;
    }
}

