package com.namenLernsystem.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.namenLernsystem.model.Student;
import com.namenLernsystem.service.Config;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    public static List<Student> readJSON() {
        List<Student> students;
        try {
            Type listType = new TypeToken<List<Student>>() {
            }.getType();

            InputStream fis = new FileInputStream(Config.getProperty("studentJSON"));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(isr);

            students = new Gson().fromJson(reader, listType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return students;
    }

    public static void writeJSON(List<Student> students) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(String.valueOf(Paths.get(Config.getProperty("studentJSON")))), students);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> readFiles(){
        List<Student> students = new ArrayList<>();
        try {
            File dir = new File(Config.getProperty("images"));
            File[] directoryListing = dir.listFiles(File::isDirectory);
            File[] directories = new File[directoryListing.length];
            for (int i = 0; i < directoryListing.length; i++) {
                directories[i] = new File(String.valueOf(directoryListing[i]));
                //System.out.println(directories[i]);
                File[] images = directories[i].listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return !file.isHidden();
                    }
                });
                for (int j = 0; j < images.length; j++) {
                    String fileName = images[j].getName();
                    if (fileName.contains(".")) {
                        fileName = fileName.substring(0, fileName.lastIndexOf("."));
                        //System.out.println(fileName);
                        byte[] bytes = StringUtils.getBytesUtf8(fileName);
                        String lol = new String(bytes, StandardCharsets.UTF_8);
                        System.out.println(lol);
                    }
                    String[] names = fileName.split("_");

                    students.add(new Student(names[0].substring(0,1).toUpperCase() +
                            names[0].substring(1), names[1].substring(0,1).toUpperCase() +
                            names[1].substring(1), directories[i].getName(), images[j].getPath()));
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
