package com.namenLernsystem.service;

import com.namenLernsystem.data.DataHandler;
import com.namenLernsystem.model.Student;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    List<Student> allStudents() throws IOException {
        return DataHandler.readJSON();
    }

    @RequestMapping(value = "/readFiles", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<String> readFiles() throws IOException {
        try {
            DataHandler.writeJSON(DataHandler.readFiles());
            return new ResponseEntity<String>("Wrote all Students to JSON-File", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Couldn't read Students", HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/load", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] loadImageFile(@QueryParam("id") String id) throws IOException {
        List<Student> students = DataHandler.readJSON();
        if (students.stream().anyMatch(student -> student.getId().equals(id))) {
            Student student = students.stream().filter(student1 -> student1.getId().equals(id))
                    .findFirst().orElseThrow(() -> new FileNotFoundException(id));
            //String path = Config.getProperty("images") + "/" + student.getPath();
            System.out.println(student.getPath());
            InputStream fis = new FileInputStream(student.getPath());
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(fis), HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<String>("Image not found", HttpStatus.NOT_FOUND).toString().getBytes();
        }
    }

    @PostMapping(value = "/updateNote")
    public @ResponseBody ResponseEntity<String> updateNote(
            @FormParam("id") String id,
            @FormParam("note") String note
    ) throws IOException {
        List<Student> students = DataHandler.readJSON();
        if (students.stream().anyMatch(student -> student.getId().equals(id))) {
            Student student = students.stream().filter(student1 -> student1.getId().equals(id))
                    .findFirst().orElseThrow(() -> new FileNotFoundException(id));
            student.setNote(note);
            DataHandler.writeJSON(students);
            return new ResponseEntity<String>("Note updated", HttpStatus.OK);
        }   else {
            return new ResponseEntity<String>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    List<Student> testCreate() throws IOException {
        //DataHandler.createStudentTest();
        return DataHandler.readJSON();
    }
}