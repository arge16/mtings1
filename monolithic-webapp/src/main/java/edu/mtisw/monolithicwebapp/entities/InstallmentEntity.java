
package edu.mtisw.monolithicwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "installments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String rut;
    private int amount;
    private double discount;
    private double interest;
    private double total;
    private String status;
    private LocalDate due_date;
    private LocalDate payment_date;
}
