package edu.mtisw.monolithicwebapp;

import org.junit.jupiter.api.Test;
import edu.mtisw.monolithicwebapp.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaymentTests {
    @Autowired
    PaymentService paymentService;


    @Test
    void testDiscount1(){
        Double discount_to_apply = paymentService.discount(2023);
        assertEquals(0.15, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount2(){
        Double discount_to_apply = paymentService.discount(2022);
        assertEquals(0.08, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount3(){
        Double discount_to_apply = paymentService.discount(2021);
        assertEquals(0.08, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount4(){
        Double discount_to_apply = paymentService.discount(2020);
        assertEquals(0.04, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount5(){
        Double discount_to_apply = paymentService.discount(2019);
        assertEquals(0.04, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount6(){
        Double discount_to_apply = paymentService.discount(2015);
        assertEquals(0, discount_to_apply, 0.0);
    }

    @Test
    void testDiscount7(){
        Double discount_to_apply = paymentService.discount(2000);
        assertEquals(0, discount_to_apply, 0.0);
    }

}
