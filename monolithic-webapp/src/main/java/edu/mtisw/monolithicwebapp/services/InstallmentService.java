package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.InstallmentEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import edu.mtisw.monolithicwebapp.repositories.InstallmentRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;

import javax.swing.text.html.Option;
import java.time.LocalDate;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class InstallmentService {
    @Autowired
    InstallmentRepository installmentRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    ExamService examService;
    public ArrayList<InstallmentEntity> getInstallments(){
        return (ArrayList<InstallmentEntity>) installmentRepository.findAll();
    }

    public void saveAll(ArrayList<InstallmentEntity> installments){
        installmentRepository.saveAll(installments);
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
            StudentEntity student = studentService.getByRut(rut);
            student.setPaymentType("Contado");
            studentService.saveStudent(student);
            LocalDate date = LocalDate.now();


            InstallmentEntity matricula = new InstallmentEntity();

            matricula.setDue_date(date);
            matricula.setRut(rut);
            matricula.setAmount(70000);
            matricula.setDiscount(0);
            matricula.setInterest(0);
            matricula.setTotal(70000+( 1500000 * 0.5));
            matricula.setStatus("Unpaid");
            installmentRepository.save(matricula);

            InstallmentEntity arancel = new InstallmentEntity();
            arancel.setDue_date(date);
            arancel.setRut(rut);
            arancel.setAmount(1500000);
            arancel.setDiscount(0.5);
            arancel.setInterest(0);
            arancel.setTotal(70000+( 1500000 * 0.5));
            arancel.setStatus("Unpaid");
            installmentRepository.save(arancel);
        }
    }


    public void generarCuotas(String rut, int cantidadCuotas) {
        if(!existsByRut(rut)){
            StudentEntity student = studentService.getByRut(rut);
            student.setPaymentType("Cuotas");
            studentService.saveStudent(student);
            LocalDate date = LocalDate.now();
            int dayOfMonth = date.getDayOfMonth();
            if (dayOfMonth < 10) {
                // Si el día actual es anterior al 10 del mes, ajusta a día 10 del mes actual
                date = date.withDayOfMonth(10);
            } else {
                // Si el día actual es igual o posterior al 10 del mes, ajusta a día 10 del mes siguiente
                date = date.plusMonths(1).withDayOfMonth(10);
            }

            double discountbygraduationyear = discountByGraduationYear(student.getGraduation_year());
            double discountbyschooltype = discountBySchoolType(student.getSchool_type());
            double totalamount =  1500000 - ((discountbygraduationyear + discountbyschooltype) * 1500000) +70000;
            double installmentAmount = totalamount / cantidadCuotas;

            int roundedInstallmentAmount = (int) Math.ceil(installmentAmount); // Redondear al entero mayor

            InstallmentEntity matricula = new InstallmentEntity();
            matricula.setDue_date(date);
            matricula.setRut(rut);
            matricula.setAmount(70000);
            matricula.setDiscount(0);
            matricula.setInterest(0);
            matricula.setTotal(70000);
            matricula.setStatus("Unpaid");

            installmentRepository.save(matricula);

            for (int i = 1; i <= cantidadCuotas; i++) {
                InstallmentEntity installment = new InstallmentEntity();
                installment.setDue_date(date);
                installment.setRut(rut);
                installment.setAmount(roundedInstallmentAmount);
                installment.setDiscount(discountbygraduationyear + discountbyschooltype);
                installment.setInterest(0);
                installment.setTotal(totalamount);
                installment.setStatus("Unpaid");
                date = date.plusMonths(1);
                installmentRepository.save(installment);

            }
        }
    }


    public ArrayList<InstallmentEntity> getAllByRut(String rut){

        return installmentRepository.findByRut(rut);
    }


    public InstallmentEntity findById(ArrayList<InstallmentEntity> installments, Long id){

        for (InstallmentEntity installment:installments) {
            if (installment.getId() == id){
                return installment;
            }
        }
        return null;
    }

    public InstallmentEntity markPaid(Long id){

        Optional<InstallmentEntity> installment = installmentRepository.findById(id);
        installment.get().setStatus("Paid");
        LocalDate paymentDay = LocalDate.now();
        installment.get().setPayment_date(paymentDay);
        return installmentRepository.save(installment.get());
    }


    public void setInterestRate(ArrayList<InstallmentEntity> installments) {
        //calcular la cuota con mas meses de atraso
        int monthsLate = 0;
        for (InstallmentEntity installment:installments) {
            if (installment.getAmount() != 70000 && installment.getStatus().equals("Unpaid") && LocalDate.now().isAfter(installment.getDue_date())) {
                monthsLate++ ;
            }
        }
        //Hasta aqui tengo la fecha mas antigua de las cuotas impagas
        double interest = interestRate(monthsLate);
        for (InstallmentEntity installment1:installments) {
            if (installment1.getAmount()!=70000 && installment1.getStatus().equals("Unpaid")){
                installment1.setInterest(interest);
                installment1.setAmount((int) (installment1.getAmount() * (1 + interest)));
                saveInstallment(installment1);
                }
        }
            
    }
    
    
    public void generateSpreadsheet(String rut){
        ArrayList<ExamEntity> exams = examService.getAllByRut(rut);
        // Recorre el ArrayList utilizando un bucle for-eachv
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateTest = LocalDate.parse(exams.get(0).getDate_of_exam(), formatter);
        int score = 0;

        for (ExamEntity examEntity : exams) {  //Aqui obtengo la fecha mas reciente de los examenes
            score = score + examEntity.getScore();
            String dateAux = examEntity.getDate_of_exam();
            LocalDate date = LocalDate.parse(dateAux, formatter);
            if (date.isAfter(dateTest)) {
                dateTest = date;
            }
        }

        int  scoreAverage = score / exams.size();

        ArrayList<InstallmentEntity> installments = getAllByRut(rut);
        for (InstallmentEntity installment : installments) {

            //Descuentos por examenes
            if (installment.getDue_date().isAfter(dateTest) ) {
                double discountByExam = discountByExam(scoreAverage);
                double totalDiscount = installment.getDiscount() + discountByExam;
                totalDiscount = Math.round(totalDiscount * 100.0) / 100.0;
                double installmentAmount = (installment.getAmount() * discountByExam) + installment.getAmount();
                installment.setDiscount(totalDiscount);
                installment.setAmount( (int) Math.ceil(installmentAmount));
                installmentRepository.save(installment);
            }
        }

        setInterestRate(installments);
    }


    public boolean existsByRut(String rut){
        return installmentRepository.existsByRut(rut);
    }


    public StudentEntity generateReport(String rut) {


        StudentEntity student = studentService.getByRut(rut);
        ArrayList<ExamEntity> exams = examService.getAllByRut(rut);
        ArrayList<InstallmentEntity> installments = getAllByRut(rut);


        double scoreAverage = 0;
        double total = 0;
        int installmentsPaid = 0;
        double debtPaid = 0;
        LocalDate lastPayment = LocalDate.MIN;
        double debtToPay = 0;
        int installmentsLate = 0;
        for (ExamEntity exam : exams) {
            scoreAverage += exam.getScore();
        }
        for (InstallmentEntity installment : installments) {
            if (installment.getAmount() != 70000) {
                total += installment.getAmount();
            }
            if (installment.getStatus().equals("Paid")) {
                installmentsPaid++;
                debtPaid += installment.getAmount();
                if (installment.getPayment_date() != null && installment.getPayment_date().isAfter(lastPayment)) {
                    lastPayment = installment.getPayment_date();
                }
            }
            if (installment.getStatus().equals("Unpaid")) {
                debtToPay += installment.getAmount();
                if (installment.getDue_date().isBefore(LocalDate.now())) {
                    installmentsLate++;
                }



            }
        }

        student.setInstallments(installments.size()-1);
        student.setTotalDebt(total);
        student.setTotalExams(exams.size());
        student.setScoresAverage(scoreAverage/exams.size());
        student.setInstallmentsPaid(installmentsPaid);
        student.setDebtPaid(debtPaid);
        if(lastPayment != LocalDate.MIN){
            student.setLastPayment(lastPayment);
        }
        else {
            student.setLastPayment(null);
        }
        student.setDebtToPay(debtToPay);
        student.setInstallmentsLate(installmentsLate);
        return student;



    }


}