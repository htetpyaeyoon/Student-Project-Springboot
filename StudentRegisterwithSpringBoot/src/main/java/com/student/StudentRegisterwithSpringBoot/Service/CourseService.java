package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.CourseBean;
import com.student.StudentRegisterwithSpringBoot.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    CourseRepo courserepo;

    public void insertCourse(CourseBean cou){
        String id=generateId();
        cou.setId(id);
        courserepo.save(cou);
    }

    public String generateId(){
        String lastId=courserepo.findLastId();
        if(lastId==null){
            return "C001";
        }else{
            int id=Integer.parseInt(lastId.substring(1))+1;
            String nextId="C"+String.format("%03d", id);
            return nextId;
        }
    }

    public void softDeleteCourse(String id) {
        Optional<CourseBean> courseOptional = courserepo.findById(id);
        courseOptional.ifPresent(course -> {
            course.setDeleted(true); // Set deleted flag to true
            courserepo.save(course);
        });
    }

    public List<CourseBean> selectAll(){
        return courserepo.findAll().stream()
                .filter(course -> course.getDeleted() == null || !course.getDeleted())
                .collect(Collectors.toList());
    }

    public CourseBean selectOne(String id){
        CourseBean course = courserepo.findById(id).orElse(null);
        return  course;
    }

    public CourseBean getCourseByName(String courseName) {
        return courserepo.findByCourseName(courseName);
    }
}
