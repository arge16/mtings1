package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.services.InstallmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaymentTests {
    @Autowired
    InstallmentService installmentService;


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


    void testInterestRateA(){
        Double interest = installmentService.interestRate(0);
        assertEquals(0, interest);
    }

    void testInterestRateB(){
        Double interest = installmentService.interestRate(1);
        assertEquals(0.03, interest);
    }

    void testInterestRateC(){
        Double interest = installmentService.interestRate(2);
        assertEquals(0.06, interest);
    }

    void testInterestRateD(){
        Double interest = installmentService.interestRate(3);
        assertEquals(0.09, interest);
    }

    void testInterestRateE(){
        Double interest = installmentService.interestRate(5);
        assertEquals(0.15, interest);
    }


}
