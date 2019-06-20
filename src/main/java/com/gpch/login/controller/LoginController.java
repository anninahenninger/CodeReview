package com.gpch.login.controller;

import javax.validation.Valid;

import com.gpch.login.model.Student;
import com.gpch.login.model.Teacher;
import com.gpch.login.service.StudentService;
import com.gpch.login.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;
    private TeacherService teacherService;

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Student student = new Student();
        modelAndView.addObject("student", student);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Student student, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Student studentExists = studentService.findStudentByEmail(student.getEmail());
        if (studentExists != null) {
            bindingResult
                    .rejectValue("email", "error.student",
                            "There is already a student registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            studentService.saveStudent(student);
            modelAndView.addObject("successMessage", "Student has been registered successfully");
            modelAndView.addObject("Student", new Student());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value="/student/homeStudent", method = RequestMethod.GET)
    public ModelAndView homeStudent(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentService.findStudentByEmail(auth.getName());
        modelAndView.addObject("studentName", "Welcome " + student.getName() + " " + student.getLastName() + " (" + student.getEmail() + ")");
        modelAndView.addObject("studentMessage","Content Available Only for students");
        modelAndView.setViewName("student/homeStudent");
        return modelAndView;
    }

    @RequestMapping(value="/teacher/homeTeacher", method = RequestMethod.GET)
    public ModelAndView homeTeacher(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Teacher teacher = teacherService.findTeacherByEmail(auth.getName());
        modelAndView.addObject("teacherName", "Welcome " + teacher.getName() + " " + teacher.getLastName() + " (" + teacher.getEmail() + ")");
        modelAndView.addObject("teacherMessage","Content Available Only for teachers");
        modelAndView.setViewName("teacher/homeTeacher");
        return modelAndView;
    }


}
