package com.student.StudentRegisterwithSpringBoot.controller;

import com.student.StudentRegisterwithSpringBoot.Service.CourseService;
import com.student.StudentRegisterwithSpringBoot.model.CourseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    CourseService couService;

    @GetMapping("/StudentRegisterwithSpringBoot/addcourse")
    public ModelAndView courseRegister(ModelMap m){
        String id=couService.generateId();
        m.addAttribute("id",id);
        return new ModelAndView("addCourse", "course", new CourseBean());
    }

    @PostMapping("/coursecreate")
    public String addCourse(@ModelAttribute("course")@Validated CourseBean obj, BindingResult br, ModelMap m) {
        if (br.hasErrors()) {
            return "addCourse";
        }
        String id=couService.generateId();
        m.addAttribute("id",id);
       List<CourseBean> list = couService.selectAll();
        for (CourseBean course : list) {
            if (course.getCourse_name().equals(obj.getCourse_name())) {
                m.addAttribute("ErrorName", "Course Name is already exists");
                return "addCourse";
            }
        }
        obj.setId(couService.generateId());
        couService.insertCourse(obj);
        return"redirect:/StudentRegisterwithSpringBoot/addcourse";
    }

    @GetMapping("/StudentRegisterwithSpringBoot/courselist")
    public String courseView(ModelMap m){
        List<CourseBean> list = couService.selectAll();
        m.addAttribute("list",list);
        return "courseView";
    }

    @GetMapping("/deletecourse/{id}")
    public String deleteCourse(@PathVariable("id") String id) {
        // Soft delete the student by updating the 'deleted' flag
        couService.softDeleteCourse(id);
        return "redirect:/StudentRegisterwithSpringBoot/courselist";
    }
}
