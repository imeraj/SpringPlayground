package com.meraj.jackrabbit.service;

import com.meraj.jackrabbit.controller.RabbitController;
import org.apache.jackrabbit.oak.Oak;
import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.plugins.document.DocumentMK;
import org.apache.jackrabbit.oak.plugins.document.DocumentNodeStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.jcr.Repository;
import java.net.UnknownHostException;

@Configuration
public class JackRabbitRepositoryBuilder {
    static Repository repo = null;
    static Logger logger = LoggerFactory.getLogger(JackRabbitRepositoryBuilder.class);


    public static Repository getRepo(String host, final int port) {
        try {
            String uri = "mongodb://" + host + ":" + port;
            DocumentNodeStore ns = new DocumentMK.Builder().setMongoDB(uri, "emp_demo", 16).getNodeStore();
            repo = new Jcr(new Oak(ns)).createRepository();
        } catch (UnknownHostException e) {
            logger.error("Exception caught: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return repo;
    }
}
