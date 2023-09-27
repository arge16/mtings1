package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.InstallmentEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.repositories.InstallmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class InstallmentService {
    @Autowired
    InstallmentRepository installmentRepository;
    @Autowired
    StudentService studentService;
    public ArrayList<InstallmentEntity> getInstallments(){
        return (ArrayList<InstallmentEntity>) installmentRepository.findAll();
    }

    public InstallmentEntity saveInstallment(InstallmentEntity installment){
        return installmentRepository.save(installment);
    }

    public Optional<InstallmentEntity> getById(Long id){
        return installmentRepository.findById(id);
    }

    public boolean deleteInstallment(Long id) {
        try{
            installmentRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }


    public double discountBySchoolType(String typeSchool) {
        double discount = 0;
        if (Objects.equals(typeSchool, "Municipal")) {
            discount = 0.2;
        } else if (Objects.equals(typeSchool, "Subvencionado")) {
            discount = 0.1;

        }
        return discount;
    }


    public double discountByGraduationYear(int graduationYear) {
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

    public int maxInstallments(String typeSchool) {
        int max = 0;
        if (Objects.equals(typeSchool, "Municipal")) {
            max = 10;
        } else if (Objects.equals(typeSchool, "Subvencionado"))  {
            max = 7;
        } else if (Objects.equals(typeSchool, "Privado")) {
            max = 4;
        }
        return max;
    }

    public double discountByExam(int examGrade) {
        double discountToApply = 0;
        if ((examGrade >= 950) && (examGrade <= 1000)) {
            discountToApply = 0.1;
        } else if ((examGrade >= 900) && (examGrade < 950))  {
            discountToApply = 0.05;
        } else if ((examGrade >= 850) && (examGrade < 900)) {
            discountToApply = 0.02;
        }
        return discountToApply;
    }

    public double interestRate(int monthsLate) {
        double interest = 0;
        if (monthsLate == 1) {
            interest = 0.03;
        } else if (monthsLate == 2)  {
            interest = 0.06;
        } else if (monthsLate == 3) {
            interest = 0.09;
        }
        else if (monthsLate > 3) {
            interest = 0.15;
        }
        return interest;
    }

    public void generarPagoContado(String rut) {
        if(!existsByRut(rut)){


        InstallmentEntity matricula = new InstallmentEntity();
        matricula.setRut(rut);
        matricula.setAmount(70000);
        matricula.setDiscount(0);
        matricula.setInterest(0);
        matricula.setTotal(70000);
        matricula.setStatus("Unpaid");
        installmentRepository.save(matricula);

        InstallmentEntity arancel = new InstallmentEntity();
        arancel.setRut(rut);
        arancel.setAmount(1500000);
        arancel.setDiscount(0.5);
        arancel.setInterest(0);
        arancel.setTotal( 1500000 * 0.5);
        arancel.setStatus("Unpaid");
        installmentRepository.save(arancel);
        }
    }


    public void generarCuotas(String rut) {
        StudentEntity student = studentService.getByRut(rut);
        if(!existsByRut(rut)){
            int maxinstallments = maxInstallments(student.getSchool_type());
            double discountbygraduationyear = discountByGraduationYear(student.getGraduation_year());
            double discountbyschooltype = discountBySchoolType(student.getSchool_type());
            double totalamount = (discountbygraduationyear + discountbyschooltype) * 1500000 + 1500000;
            for (int i = 1; i <= maxinstallments; i++) {
                InstallmentEntity installment = new InstallmentEntity();
                installment.setRut(rut);
                installment.setAmount(totalamount / maxinstallments);
                installment.setDiscount(discountbygraduationyear + discountbyschooltype);
                installment.setInterest(0);
                installment.setTotal(totalamount);
                installment.setStatus("Unpaid");
                installmentRepository.save(installment);

            }
            InstallmentEntity matricula = new InstallmentEntity();
            matricula.setRut(rut);
            matricula.setAmount(70000);
            matricula.setDiscount(0);
            matricula.setInterest(0);
            matricula.setTotal(70000);
            matricula.setStatus("Unpaid");
            installmentRepository.save(matricula);
        }
    }


    public ArrayList<InstallmentEntity> getByRut(String rut){



        return installmentRepository.findByRut(rut);
    }

    public InstallmentEntity markPaid(Long id){

        Optional<InstallmentEntity> installment = installmentRepository.findById(id);
        installment.get().setStatus("Paid");
        return installmentRepository.save(installment.get());
    }


    public boolean existsByRut(String rut){
        return installmentRepository.existsByRut(rut);
    }

}