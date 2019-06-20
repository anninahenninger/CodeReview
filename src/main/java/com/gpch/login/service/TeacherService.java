package com.gpch.login.service;

import com.gpch.login.model.Role;
import com.gpch.login.model.Teacher;
import com.gpch.login.repository.RoleRepository;
import com.gpch.login.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service("teacherService")
public class TeacherService {

    private TeacherRepository teacherRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository,
                          RoleRepository roleRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Teacher findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    public Teacher saveTeacher(Teacher teacher) {
        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        teacher.setActive(1);
        Role teacherRole = roleRepository.findByRole("TEACHER");
        teacher.setRoles(new HashSet<Role>(Arrays.asList(teacherRole)));
        return teacherRepository.save(teacher);
    }
}