package com.epam.services;

import com.epam.CourseType;

import java.util.ArrayList;
import java.util.List;


public class CourseService {

    public List getAvailableCourses(CourseType type){

        List names = new ArrayList();

        if(type.equals(CourseType.JAVA)){
            names.add("Java for beginners");
            names.add(("OOP in Java"));

        }else if(type.equals(CourseType.PYTHON)){
            names.add("Python for complete beginners");
            names.add("Python for data science");

        }else if(type.equals(CourseType.CSHARP)){
            names.add("C# for beginners");

        }else {
            names.add("No Course Available");
        }
        return names;
    }
}
