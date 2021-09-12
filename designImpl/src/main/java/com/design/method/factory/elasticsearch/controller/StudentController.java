package com.design.method.factory.elasticsearch.controller;


import com.design.method.api.Response.Response;
import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.elasticsearch.dto.Student;
import com.design.method.factory.elasticsearch.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/elasticsearch")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/getStudentInfo")
    public Response<List<Student>> getStudentInfoByClassNumber(@RequestParam String classNumber) {
        try {
            Map<String, Object> map = new HashMap();
            map.put("classNumber",classNumber);
            return studentService.getStudentInfoByMultiQuery(map);
        }catch (DesignMethodFactoryException de) {
            return handleError(de);
        }
    }

    @RequestMapping("/updateStudentInfo")
    public Response updateStudentInfo(@RequestBody Student student) {
        try {
            return studentService.updateStudentInfo(student);
        }catch (DesignMethodFactoryException de) {
            return handleError(de);
        }
    }

    @RequestMapping("/deleteStudentInfo")
    public Response deleteStudentInfo(@RequestParam String id) {
        try {
            return studentService.deleteStudentInfo(id);
        }catch (DesignMethodFactoryException de) {
            return handleError(de);
        }
    }

    @RequestMapping("/addStudentInfo")
    public Response addStudentInfo(@RequestBody Student student) {
        try {
            return studentService.addStudentInfo(student);
        }catch (DesignMethodFactoryException de) {
            return handleError(de);
        }

    }

    private Response handleError(DesignMethodFactoryException de) {
        return new Response().ERROR(de.getErrorCode());
    }

}
