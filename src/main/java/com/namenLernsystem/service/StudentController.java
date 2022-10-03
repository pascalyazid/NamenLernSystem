package com.namenLernsystem.service

import com.namenLernsystem.data.DataHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
public class StudentController {

@RequestMapping(value = "/list", method = RequestMethod.GET)

}