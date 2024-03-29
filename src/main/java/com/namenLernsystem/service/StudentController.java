package com.namenLernsystem.service;

import com.namenLernsystem.data.DataHandler;
import com.namenLernsystem.model.HistoryEntry;
import com.namenLernsystem.model.Student;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Encoded;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Encoded
    @ResponseBody
    ResponseEntity<List<Student>> allStudents() throws IOException {
        System.out.println(DataHandler.readJSON().get(15).getLastName());
        return new ResponseEntity<List<Student>>(DataHandler.readJSON(), HttpStatus.OK);
    }

    @RequestMapping(value = "/readFiles", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<String> readFiles() throws IOException {
        try {

            DataHandler.writeJSON(DataHandler.readFiles());
            ResponseEntity<String > response = new ResponseEntity<>("Wrote all Students to JSON-File", HttpStatus.OK);
            return response;
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
            InputStream fis = new FileInputStream(student.getPath());
            return new ResponseEntity<byte[]>(IOUtils.toByteArray(fis), HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<String>("Image not found", HttpStatus.NOT_FOUND).toString().getBytes();
        }
    }
    @PostMapping(value = "/writeHistory")
    public void writeHistroy(
          @FormParam("message") String message
    ){
       HistoryEntry historyEntry = new HistoryEntry(message);
       List<HistoryEntry> historyEntries = DataHandler.readHistoryJSON();
       historyEntries.add(historyEntry);
       DataHandler.writeHistoryJSON(historyEntries);
    }

    @RequestMapping(value = "/listHistory")
    public ResponseEntity<List<HistoryEntry>> writeHistroy(){
        return new ResponseEntity<List<HistoryEntry>>(DataHandler.readHistoryJSON(), HttpStatus.OK);
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
        return DataHandler.readJSON();
    }
}