package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public ArrayList<StudentEntity> getStudents(){
        return (ArrayList<StudentEntity>) studentRepository.findAll();
    }

    public StudentEntity saveStudent(StudentEntity student){
        return studentRepository.save(student);
    }

    public Optional<StudentEntity> getById(Long id){
        return studentRepository.findById(id);
    }

    public boolean deleteStudent(Long id) {
        try{
            studentRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

}