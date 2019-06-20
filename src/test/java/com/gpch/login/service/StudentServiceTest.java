package com.gpch.login.service;

import com.gpch.login.model.Student;
import com.gpch.login.repository.RoleRepository;
import com.gpch.login.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class StudentServiceTest {

    @Mock
    private StudentRepository mockStudentRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private StudentService studentServiceUnderTest;
    private Student student;

    @Before
    public void setUp() {
        initMocks(this);
        studentServiceUnderTest = new StudentService(mockStudentRepository,
                mockRoleRepository,
                mockBCryptPasswordEncoder);
        student = Student.builder()
                .id(1)
                .name("Max")
                .lastName("Mustermann")
                .email("test@test.com")
                .build();

        Mockito.when(mockStudentRepository.save(any()))
                .thenReturn(student);
        Mockito.when(mockStudentRepository.findByEmail(anyString()))
                .thenReturn(student);
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        final Student result = studentServiceUnderTest.findStudentByEmail(email);

        // Verify the results
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testSaveUser() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        Student result = studentServiceUnderTest.saveStudent(Student.builder().build());

        // Verify the results
        assertEquals(email, result.getEmail());
    }
}
