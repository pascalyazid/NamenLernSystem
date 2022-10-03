package com.namenLernsystem.service;

import com.namenLernsystem.data.DataHandler;
import com.namenLernsystem.model.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    List<Student> allStudents() throws IOException {
        return DataHandler.readJSON();
    }
}