package edu.mtisw.monolithicwebapp;
import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import edu.mtisw.monolithicwebapp.repositories.ExamRepository;
import edu.mtisw.monolithicwebapp.services.ExamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExamTests {

    @Autowired
    ExamService examService;
    @Autowired
    ExamRepository examRepository;

    @Test
    void testGetExams(){
        ExamEntity newExam = new ExamEntity();
        newExam.setRut("99-8");
        newExam.setScore(890);
        newExam.setDate_of_exam("00/00/0000");
        examService.saveExam(newExam);
        assertNotNull(examService.getExams());
        examRepository.delete(newExam);
    }

    @Test
    void testSaveExam() {
        ExamEntity newExam = new ExamEntity();
        newExam.setRut("99-8");
        newExam.setScore(890);
        newExam.setDate_of_exam("00/00/0000");
        examService.saveExam(newExam);
        assertEquals("99-8",examService.getById(newExam.getId()).get().getRut());
        assertEquals(890,examService.getById(newExam.getId()).get().getScore());
        assertEquals("00/00/0000",examService.getById(newExam.getId()).get().getDate_of_exam());
        examRepository.delete(newExam);
    }

    @Test
    void testGuardarDataDB(){
        examService.guardarDataDB("2023/10/06", "11.222.333-4", "800");
        ExamEntity newExam = examService.getAllByRut("11.222.333-4").get(0);
        assertEquals("11.222.333-4",newExam.getRut());
        examService.eliminarData(newExam);
    }



}
