package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ExamService examService;

    public ArrayList<StudentEntity> getStudents() {
        return (ArrayList<StudentEntity>) studentRepository.findAll();
    }

    public StudentEntity saveStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    public StudentEntity getByRut(String rut) {
        return studentRepository.findByRut(rut);
    }

    public void saveStudentData(String rut, String name, String lastname, String birthdate, int graduation_year, String school, String school_type) {
        StudentEntity student = new StudentEntity();
        student.setRut(rut);
        student.setName(name);
        student.setLastname(lastname);
        student.setBirthdate(birthdate);
        student.setGraduation_year(graduation_year);
        student.setSchool(school);
        student.setSchool_type(school_type);
        studentRepository.save(student);
    }

}