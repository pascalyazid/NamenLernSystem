package com.namenLernsystem.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.namenLernsystem.model.Student;
import com.namenLernsystem.service.Config;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DataHandler {

    public List<Student> readJSON() {
        List<Student> students;
        try {
            Type listType = new TypeToken<List<Student>>() {
            }.getType();

            InputStream fis = new FileInputStream(Config.getProperty("imageJSON"));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(isr);

            students = new Gson().fromJson(reader, listType);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return students;
    }

    public void writeJSON(List<Student> students) {


    }
}
