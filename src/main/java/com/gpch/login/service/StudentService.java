package com.gpch.login.service;

import com.gpch.login.model.Role;
import com.gpch.login.model.Student;
import com.gpch.login.repository.RoleRepository;
import com.gpch.login.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("studentService")
public class StudentService {

    private StudentRepository studentRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          RoleRepository roleRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Student findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    public Student saveStudent(Student student) {
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        student.setActive(1);
        Role studentRole = roleRepository.findByRole("ADMIN");
        student.setRoles(new HashSet<Role>(Arrays.asList(studentRole)));
        return studentRepository.save(student);
    }
}