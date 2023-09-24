package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.PaymentEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.repositories.PaymentRepository;

import edu.mtisw.monolithicwebapp.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    StudentRepository studentRepository;

    public ArrayList<PaymentEntity> getPayments(){
        return (ArrayList<PaymentEntity>) paymentRepository.findAll();
    }

    public PaymentEntity savePayment(PaymentEntity payment){
        return paymentRepository.save(payment);
    }

    public Optional<PaymentEntity> getById(Long id){
        return paymentRepository.findById(id);
    }

    public boolean deletePayment(Long id) {
        try{
            paymentRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }


    public PaymentEntity pagoContado(String rut) {

        PaymentEntity payment = new PaymentEntity();
        StudentEntity student = studentRepository.findByRut(rut);
        if (student.getInstallments()=="No") {
            payment.setArancel(750000);
        }
        return paymentRepository.save(payment);
    }

    public Double discount(int graduationYear) {
    int yearsGraduated = 2023 - graduationYear;
    double discountToApply = 0;
        if (yearsGraduated < 1) {
                discountToApply = 0.15;
        } else if (yearsGraduated == 1 ||  yearsGraduated == 2)  {
                discountToApply = 0.08;
        } else if (yearsGraduated == 3 ||  yearsGraduated == 4) {
                discountToApply = 0.04;
        }
        return discountToApply;
    }



    /*


    public PaymentEntity discount(String rut) {

        PaymentEntity payment = new PaymentEntity();
        StudentEntity student = studentRepository.findByRut(rut);
        int yearsGraduated = 2023 - student.getGraduation_year();
        double discount = 0;
        if (yearsGraduated < 1) {
            discount = 0.15;
        } else if (yearsGraduated == 1 ||  yearsGraduated == 2)  {
            discount = 0.08;
        } else if (yearsGraduated == 3 ||  yearsGraduated == 4) {
            discount = 0.05;
        }else if (yearsGraduated >= 5) {
            discount = 0;
        }
        //agrega el codigo para calcular el descuento segun notas examen

        return paymentRepository.save(payment);
    }

  */

}