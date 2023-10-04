package edu.mtisw.monolithicwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String rut;
    private String name;
    private String lastname;
    private String birthdate;
    private int graduation_year;
    private String school;
    private String school_type;

    private int totalExams; //total de examenes
    private double scoresAverage; //promedio de notas
    private double totalDebt; //deuda total
    private String paymentType; //tipo de pago Contado o Cuotas
    private int installments; //cuotas pactadas
    private int installmentsPaid; //cuotas pagadas
    private double debtPaid; //deuda pagada
    private LocalDate lastPayment; //ultima fecha de pago
    private double debtToPay; //deuda por pagar
    private int installmentsLate; //cuotas atrasadas

}
