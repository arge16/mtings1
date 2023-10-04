package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import edu.mtisw.monolithicwebapp.entities.InstallmentEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

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

    public Optional<StudentEntity> getById(Long id) {
        return studentRepository.findById(id);
    }

    public StudentEntity getByRut(String rut) {
        return studentRepository.findByRut(rut);
    }

    public boolean deleteStudent(Long id) {
        try {
            studentRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
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