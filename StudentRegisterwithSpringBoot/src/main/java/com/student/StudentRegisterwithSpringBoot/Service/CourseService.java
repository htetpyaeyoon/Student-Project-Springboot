package com.student.StudentRegisterwithSpringBoot.Service;

import com.student.StudentRegisterwithSpringBoot.model.CourseBean;
import com.student.StudentRegisterwithSpringBoot.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

   /* public void deleteCourse(String id){
        courserepo.deleteById(id);
    }*/

    public List<CourseBean> selectAll(){
        List<CourseBean> list=courserepo.findAll();
        return list;
    }

    public CourseBean selectOne(String id){
        CourseBean course = courserepo.findById(id).orElse(null);
        return  course;
    }

    public CourseBean getCourseByName(String courseName) {
        return courserepo.findByCourseName(courseName);
    }
}
