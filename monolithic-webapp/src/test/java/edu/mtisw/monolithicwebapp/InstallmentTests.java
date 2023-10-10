package edu.mtisw.monolithicwebapp;
import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import edu.mtisw.monolithicwebapp.entities.InstallmentEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.repositories.ExamRepository;
import edu.mtisw.monolithicwebapp.repositories.StudentRepository;
import edu.mtisw.monolithicwebapp.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import edu.mtisw.monolithicwebapp.repositories.InstallmentRepository;
import edu.mtisw.monolithicwebapp.services.InstallmentService;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class InstallmentTests {

    @Autowired
    InstallmentService installmentService;
    @Autowired
    InstallmentRepository installmentRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    ExamRepository examRepository;

    @Test
    void testGetInstallments(){
        InstallmentEntity newInstallment = new InstallmentEntity();
        newInstallment.setRut("99-8");
        newInstallment.setInterest(0.2);
        newInstallment.setDiscount(0.2);
        newInstallment.setAmount(400);
        installmentService.saveInstallment(newInstallment);
        assertNotNull(installmentService.getInstallments());
        installmentRepository.delete(newInstallment);
    }


    @Test
    void testGetById(){
        InstallmentEntity newInstallment = new InstallmentEntity();
        newInstallment.setRut("8867-8");
        newInstallment.setInterest(0.3);
        newInstallment.setDiscount(0.3);
        newInstallment.setAmount(400);
        installmentRepository.save(newInstallment);
        assertNotNull(installmentService.getById(newInstallment.getId()));
        installmentRepository.delete(newInstallment);
    }


    @Test
    void testDiscountBySchoolTypeMunicipal(){
        Double discount = installmentService.discountBySchoolType("Municipal");
        assertEquals(0.2, discount);
    }

    @Test
    void testDiscountBySchoolTypeSubvencionado(){
        Double discount = installmentService.discountBySchoolType("Subvencionado");
        assertEquals(0.1, discount);
    }

    @Test
    void testDiscountBySchoolTypePrivado(){
        Double discount = installmentService.discountBySchoolType("Privado");
        assertEquals(0, discount);
    }


    @Test
    void testDiscount1(){
        Double discount_to_apply = installmentService.discountByGraduationYear(2023);
        assertEquals(0.15, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount2(){
        Double discount_to_apply = installmentService.discountByGraduationYear(2022);
        assertEquals(0.08, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount3(){
        Double discount_to_apply = installmentService.discountByGraduationYear(2021);
        assertEquals(0.08, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount4(){
        Double discount_to_apply = installmentService.discountByGraduationYear(2020);
        assertEquals(0.04, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount5(){
        Double discount_to_apply = installmentService.discountByGraduationYear(2019);
        assertEquals(0.04, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount6(){
        Double discount_to_apply = installmentService.discountByGraduationYear(2015);
        assertEquals(0, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount7(){
        Double discount_to_apply = installmentService.discountByGraduationYear(2000);
        assertEquals(0, discount_to_apply, 0.0);
    }

    @Test
    void testMaxInstallmentsMunicipal(){
        int max_installments = installmentService.maxInstallments("Municipal");
        assertEquals(10, max_installments);
    }

    @Test
    void testMaxInstallmentsSubvencionado(){
        int max_installments = installmentService.maxInstallments("Subvencionado");
        assertEquals(7, max_installments);
    }

    @Test
    void testMaxInstallmentsPrivado(){
        int max_installments = installmentService.maxInstallments("Privado");
        assertEquals(4, max_installments);
    }

    @Test
    void testDiscountByExamA(){
        Double discount = installmentService.discountByExam(957);
        assertEquals(0.1, discount);
    }


    @Test
    void testDiscountByExamB(){
        Double discount = installmentService.discountByExam(910);
        assertEquals(0.05, discount);
    }


    @Test
    void testDiscountByExamC(){
        Double discount = installmentService.discountByExam(870);
        assertEquals(0.02, discount);
    }


    @Test
    void testDiscountByExamD(){
        Double discount = installmentService.discountByExam(700);
        assertEquals(0, discount);
    }

    @Test
    void testInterestRateA(){
        Double interest = installmentService.interestRate(0);
        assertEquals(0, interest);
    }
    @Test
    void testInterestRateB(){
        Double interest = installmentService.interestRate(1);
        assertEquals(0.03, interest);
    }
    @Test
    void testInterestRateC(){
        Double interest = installmentService.interestRate(2);
        assertEquals(0.06, interest);
    }
    @Test
    void testInterestRateD(){
        Double interest = installmentService.interestRate(3);
        assertEquals(0.09, interest);
    }
    @Test
    void testInterestRateE(){
        Double interest = installmentService.interestRate(5);
        assertEquals(0.15, interest);
    }

    @Test
    void testGenerarPagoContado(){
        StudentEntity newStudent = new StudentEntity();
        newStudent.setRut("11.222.333-4");
        studentService.saveStudent(newStudent);
        installmentService.generarPagoContado("11.222.333-4");
        assertNotNull(installmentService.getAllByRut("11.222.333-4"));
        assertEquals("Contado", studentService.getByRut("11.222.333-4").getPaymentType());
        for (InstallmentEntity installment : installmentService.getAllByRut("11.222.333-4")){
            installmentRepository.delete(installment);
        }
        studentRepository.delete(newStudent);

    }

    @Test
    void testGenerarCuotas(){

        StudentEntity newStudent = new StudentEntity();
        newStudent.setRut("00.000.000-0");
        studentService.saveStudent(newStudent);
        installmentService.generarCuotas("00.000.000-0", 4);
        assertNotNull(installmentService.getAllByRut("00.000.000-0"));
        assertEquals("Cuotas", studentService.getByRut("00.000.000-0").getPaymentType());
        for (InstallmentEntity installment : installmentService.getAllByRut("00.000.000-0")){
            installmentRepository.delete(installment);
        }
        studentRepository.delete(newStudent);

    }

    @Test
    void testMarkPaid(){
        InstallmentEntity newInstallment = new InstallmentEntity();
        newInstallment.setStatus("Unpaid");
        installmentRepository.save(newInstallment);
        installmentService.markPaid(newInstallment.getId());
        assertEquals("Paid", installmentService.getById(newInstallment.getId()).get().getStatus()  );
        installmentRepository.delete(newInstallment);
    }


    @Test
    void testSetInterestRate(){
        LocalDate fechaEspecifica1 = LocalDate.of(2023, 10, 8);
        LocalDate fechaEspecifica2 = LocalDate.of(2023, 11, 15);
        ArrayList<InstallmentEntity> installmentList = new ArrayList<>();
        InstallmentEntity newInstallment1 = new InstallmentEntity();
        InstallmentEntity newInstallment2 = new InstallmentEntity();
        newInstallment1.setInterest(0);
        newInstallment1.setStatus("Unpaid");
        newInstallment1.setAmount(800000);
        newInstallment1.setDue_date(fechaEspecifica1);
        newInstallment2.setInterest(0);
        newInstallment2.setStatus("Unpaid");
        newInstallment2.setAmount(800000);
        newInstallment2.setDue_date(fechaEspecifica2);
        installmentList.add(newInstallment1);
        installmentList.add(newInstallment2);
        installmentRepository.save(newInstallment1);
        installmentRepository.save(newInstallment2);
        installmentService.setInterestRate(installmentList);
        assertEquals(0.03, installmentService.getById(newInstallment1.getId()).get().getInterest());
        installmentRepository.delete(newInstallment1);
        installmentRepository.delete(newInstallment2);
    }


    @Test
    void testGenerateSpreadsheet(){
        ExamEntity newExam = new ExamEntity();
        newExam.setRut("00.000.000-0");
        newExam.setScore(957);
        newExam.setDate_of_exam("27-08-2023");
        examRepository.save(newExam);

        ExamEntity newExam1 = new ExamEntity();
        newExam1.setRut("00.000.000-0");
        newExam1.setScore(950);
        newExam1.setDate_of_exam("27-09-2023");
        examRepository.save(newExam1);

        LocalDate fechaEspecifica1 = LocalDate.of(2023, 9, 8);
        LocalDate fechaEspecifica2 = LocalDate.of(2023, 10, 15);
        InstallmentEntity newInstallment1 = new InstallmentEntity();
        InstallmentEntity newInstallment2 = new InstallmentEntity();
        newInstallment1.setRut("00.000.000-0");
        newInstallment1.setStatus("Paid");
        newInstallment1.setAmount(800000);
        newInstallment1.setDue_date(fechaEspecifica1);
        newInstallment2.setRut("00.000.000-0");
        newInstallment2.setStatus("Unpaid");
        newInstallment2.setAmount(800000);
        newInstallment2.setDue_date(fechaEspecifica2);
        installmentRepository.save(newInstallment1);
        installmentRepository.save(newInstallment2);
        installmentService.generateSpreadsheet("00.000.000-0");

        assertEquals(0.1, installmentService.getById(newInstallment2.getId()).get().getDiscount());
        installmentRepository.delete(newInstallment1);
        installmentRepository.delete(newInstallment2);
        examRepository.delete(newExam);
        examRepository.delete(newExam1);

    }

    @Test
    void testGenerateReport(){

        StudentEntity newStudent = new StudentEntity();
        newStudent.setRut("00.000.000-0");
        studentRepository.save(newStudent);

        ExamEntity newExam = new ExamEntity();
        newExam.setRut("00.000.000-0");
        newExam.setScore(957);
        newExam.setDate_of_exam("27-08-2023");
        examRepository.save(newExam);

        ExamEntity newExam1 = new ExamEntity();
        newExam1.setRut("00.000.000-0");
        newExam1.setScore(950);
        newExam1.setDate_of_exam("27-09-2023");
        examRepository.save(newExam1);

        LocalDate fechaEspecifica1 = LocalDate.of(2023, 9, 8);
        LocalDate fechaEspecifica2 = LocalDate.of(2023, 10, 15);
        InstallmentEntity newInstallment1 = new InstallmentEntity();
        InstallmentEntity newInstallment2 = new InstallmentEntity();
        newInstallment1.setRut("00.000.000-0");
        newInstallment1.setStatus("Paid");
        newInstallment1.setAmount(800000);
        newInstallment1.setDue_date(fechaEspecifica1);
        newInstallment1.setPayment_date(LocalDate.of(2023, 9, 20));
        newInstallment2.setRut("00.000.000-0");
        newInstallment2.setStatus("Unpaid");
        newInstallment2.setAmount(900000);
        newInstallment2.setDue_date(fechaEspecifica2);
        installmentRepository.save(newInstallment1);
        installmentRepository.save(newInstallment2);
       StudentEntity studentGenerated = installmentService.generateReport("00.000.000-0");
        assertEquals(953.5, studentGenerated.getScoresAverage());
        assertEquals(1, studentGenerated.getInstallments());
        assertEquals(800000, studentGenerated.getDebtPaid());
        assertEquals(900000, studentGenerated.getDebtToPay());
        assertEquals(1700000, studentGenerated.getTotalDebt());
        assertEquals(2, studentGenerated.getTotalExams());
        assertEquals(LocalDate.of(2023, 9, 20), studentGenerated.getLastPayment());
        installmentRepository.delete(newInstallment1);
        installmentRepository.delete(newInstallment2);
        examRepository.delete(newExam);
        examRepository.delete(newExam1);
        studentRepository.delete(newStudent);


    }

}
