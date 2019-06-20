package jp.co.isopra.lunchmap.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PathController {

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void index() throws Throwable {
    String filepath = "images/ChIJD4tLmA-MGGARqHJvbMa-yNs"; // src/main/resources 配下の相対パス
    Resource resource = resourceLoader.getResource(filepath);

    File file = resource.getFile();
    System.out.println("file.exists(): " + file.exists());
    System.out.println("file.getAbsolutePath(): " + file.getAbsolutePath());
    }
//    public void index() throws Throwable {
//        String filepath = "static/images/ChIJD4tLmA-MGGARqHJvbMa-yNs"; // src/main/resources 配下の相対パス
//        Resource resource = resourceLoader.getResource("classpath:" + filepath);
//
//        String name = resource.getFilename();
//        System.out.println("name: " + name);
//
//        File file = resource.getFile();
//        System.out.println("file.exists(): " + file.exists());
//        System.out.println("file.getAbsolutePath(): " + file.getAbsolutePath());
//    }
}