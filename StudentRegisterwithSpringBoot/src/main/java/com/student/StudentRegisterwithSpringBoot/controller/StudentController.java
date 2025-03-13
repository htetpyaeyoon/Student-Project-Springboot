package com.student.StudentRegisterwithSpringBoot.controller;

import com.student.StudentRegisterwithSpringBoot.Service.AttendService;
import com.student.StudentRegisterwithSpringBoot.Service.CourseService;
import com.student.StudentRegisterwithSpringBoot.Service.StudentService;
import com.student.StudentRegisterwithSpringBoot.model.CourseBean;
import com.student.StudentRegisterwithSpringBoot.model.StudentAttendBean;
import com.student.StudentRegisterwithSpringBoot.model.StudentBean;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    AttendService attendService;

    @GetMapping("/StudentRegisterwithSpringBoot/Student")
    public ModelAndView studentRegister(ModelMap m){
        List<CourseBean> list=courseService.selectAll();
        m.addAttribute("list",list);
        return new ModelAndView("addStudent","stud",new StudentBean());
    }

    @PostMapping("/addstudent")
    public String register(@ModelAttribute("stud") StudentBean obj, ModelMap m, @RequestParam("file") MultipartFile file,@RequestParam("attend") String id) {
        if (!file.isEmpty()) {
            String originalFileNameString = file.getOriginalFilename();
            String uploadDir = "D:/Htet Pyae Yoon/StudentRegisterwithSpringBoot (1)/StudentRegisterwithSpringBoot/src/main/resources/static/images/"
                    + originalFileNameString;
            String newFile = "/images/" + originalFileNameString;

            try {
                FileOutputStream fos = new FileOutputStream(uploadDir);
                InputStream is = file.getInputStream();
                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            String lastId = studentService.generateId();
            obj.setId(lastId);
            obj.setPhoto(newFile);
        }
        studentService.insertStuent(obj);
        Optional<StudentBean> studentOptional=studentService.getStudentByid(obj.getId());
        StudentBean student = studentOptional.orElseThrow(() -> new RuntimeException("Student not found with ID: " + obj.getId()));
       String [] attend=obj.getAttend();
       if(attend!=null){
           for(String courseId:attend){
               CourseBean  course=courseService.selectOne(courseId);
               StudentAttendBean studentAttend = new StudentAttendBean();
               studentAttend.setStudent(student);
               studentAttend.setCourse(course);
               attendService.insert(studentAttend);
           }
       }

        return "redirect:/StudentRegisterwithSpringBoot/Student";
    }

    @GetMapping("/StudentRegisterwithSpringBoot/studentlist")
    public String studentView(ModelMap m){
        List<StudentBean> list= studentService.selectAllStudent();
        m.addAttribute("list",list);
        return "StudentList";
    }

    @GetMapping("/setupstudentUpdate/{id}")
    public ModelAndView setupUpdate(@PathVariable("id") String id, ModelMap m){
    StudentBean bean=studentService.selectOne(id);
    m.addAttribute("student",bean);

    String photoUrl=bean.getPhoto();
    m.addAttribute("photoUrl", photoUrl);

    List<CourseBean> course=courseService.selectAll();
    m.addAttribute("course",course);

    List<StudentAttendBean> attend=attendService.findByStudentId(id);
    m.addAttribute("attend",attend);
        return new ModelAndView("updateStudent", "student",studentService.selectOne(id));
    }

    @PostMapping("/updatestuend")
    @Transactional
    public String UpdateStudent(@ModelAttribute("student") StudentBean obj,ModelMap m, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String originalFileNameString = file.getOriginalFilename();
            String uploadDir = "D:/Htet Pyae Yoon/StudentRegisterwithSpringBoot (1)/StudentRegisterwithSpringBoot/src/main/resources/static/images/"
                    + originalFileNameString;
            String newFile = "/images/" + originalFileNameString;

            try {
                FileOutputStream fos = new FileOutputStream(uploadDir);
                InputStream is = file.getInputStream();
                byte[] data = new byte[is.available()];
                is.read(data);
                fos.write(data);
                fos.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            obj.setPhoto(newFile);
        }else {
            // Retrieve existing student from the database to retain the existing photo
            Optional<StudentBean> existingStudentOptional = studentService.getStudentByid(obj.getId());
            if (existingStudentOptional.isPresent()) {
                StudentBean existingStudent = existingStudentOptional.get();
                obj.setPhoto(existingStudent.getPhoto());
            }
        }
            studentService.update(obj);
            String[] attend = obj.getAttend();
        attendService.deleteStudentByid(obj.getId());
            if (attend != null) {
                for (String courseId : attend) {
                    CourseBean course = courseService.selectOne(courseId);
                    StudentAttendBean studentAttend = new StudentAttendBean();
                    studentAttend.setStudent(obj);
                    studentAttend.setCourse(course);
                    attendService.insert(studentAttend);
                }
            }
        return "redirect:/StudentRegisterwithSpringBoot/studentlist";
        }

    @GetMapping("/deletestu/{id}")
    public String deleteStudent(@PathVariable("id") String id) {
        // Soft delete the student by updating the 'deleted' flag
        studentService.softDeleteStudent(id);
        return "redirect:/StudentRegisterwithSpringBoot/studentlist";
    }

    }





