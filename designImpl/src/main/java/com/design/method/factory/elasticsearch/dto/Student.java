package com.design.method.factory.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private String id;

    private String name;

    private String address;

    private String sex;

    private String className;

    private String classNumber;

    private String age;

    private String school;

    private String classMaster;

    private String classRange;

}
