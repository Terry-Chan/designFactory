package com.design.method.factory.elasticsearch.service;

import com.design.method.api.Response.Response;
import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.annotation.PreCheck;
import com.design.method.factory.elasticsearch.dto.Student;
import com.design.method.factory.elasticsearch.util.ElasticSerachUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {


    @Autowired
    private RestHighLevelClient client;

    private static final String STUDENT_INDEX = "student_index";


    @PreCheck(indexName = STUDENT_INDEX)
    public Response<List<Student>> getStudentInfoByMultiQuery(Map map) throws DesignMethodFactoryException{
        return new Response().SUCCESS(ElasticSerachUtil.getResult(client,STUDENT_INDEX,map,Student.class));
    }

    @PreCheck(indexName = STUDENT_INDEX)
    public Response updateStudentInfo(Student student) throws DesignMethodFactoryException{
        ElasticSerachUtil.updateDate(client,STUDENT_INDEX,student,student.getId());
        return new Response().SUCCESS(null);
    }

    @PreCheck(indexName = STUDENT_INDEX)
    public Response deleteStudentInfo(String id) throws DesignMethodFactoryException{
        ElasticSerachUtil.deleteData(client,STUDENT_INDEX,id);
        return new Response().SUCCESS(null);
    }

    @PreCheck(indexName = STUDENT_INDEX)
    public Response addStudentInfo(Student student) throws DesignMethodFactoryException{
        ElasticSerachUtil.addDocument(client,STUDENT_INDEX,student.getId(),student);
        return new Response().SUCCESS(null);
    }

}
