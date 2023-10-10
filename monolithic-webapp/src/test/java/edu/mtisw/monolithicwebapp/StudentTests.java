package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.repositories.StudentRepository;
import edu.mtisw.monolithicwebapp.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentTests {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;


    @Test
    void testGetExams(){
        StudentEntity newStudent = new StudentEntity();
        newStudent.setRut("98.765.432-1");
        studentService.saveStudent(newStudent);
        assertNotNull(studentService.getStudents());
        studentRepository.delete(newStudent);
    }

    @Test
    void testSaveStudentData(){
        studentService.saveStudentData("98.765.432-1", "Argenis", "Benitez", "1997/26/03", 2014, "Tirso", "Privado");
        StudentEntity student = studentService.getByRut("98.765.432-1");
        assertEquals("98.765.432-1", student.getRut());
        assertEquals("Argenis", student.getName());
        assertEquals("Benitez", student.getLastname());
        assertEquals("1997/26/03", student.getBirthdate());
        assertEquals(2014, student.getGraduation_year());
        assertEquals("Tirso", student.getSchool());
        assertEquals("Privado", student.getSchool_type());
        studentRepository.delete(student);
    }

}
