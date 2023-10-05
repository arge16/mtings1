package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.InstallmentEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.services.InstallmentService;
import edu.mtisw.monolithicwebapp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping
public class InstallmentController {
    @Autowired
    StudentService studentService;
    @Autowired
    InstallmentService installmentService;

    @GetMapping("/generar-contado/{rut}")
    public String generarCuotasContado(@PathVariable("rut") String rut) {
       // StudentEntity student = studentService.getByRut(rut);
        installmentService.generarPagoContado(rut);

        return "redirect:/students"; // Reemplaza "ruta-de-destino" con la URL adecuada
    }


    @GetMapping("/Generate-report/{rut}")
    public String generarReporte( Model model,
                                  @PathVariable String rut){
        model.addAttribute("student",installmentService.generateReport(rut));
        return "resumen";
    }

    @GetMapping ("/mark-paid/{id}")
    public String markPaid(@PathVariable("id") Long id) {
        // StudentEntity student = studentService.getByRut(rut);
        installmentService.markPaid(id);
        // Después de marcar el pago, redirige a listar-cuotas con el parámetro 'rut' en la ruta
        InstallmentEntity installment = installmentService.getById(id).orElse(null);
        if (installment != null) {
            String rut = installment.getRut();
            return "redirect:/listar-cuotas/" + rut;
        } else {
            // Manejo de error si no se encuentra la cuota
            return "redirect:/error"; // Puedes redirigir a una página de error o hacer algo más aquí
        }

    }
    @GetMapping("/generar-cuotas/{rut}")
    public String generarCuotas(@PathVariable("rut") String rut, Model model) {
        StudentEntity student = studentService.getByRut(rut);
        int limit = installmentService.maxInstallments(student.getSchool_type());
        model.addAttribute("limit",limit);
        model.addAttribute("student",student);
        //installmentService.generarCuotas(rut);
        // Aquí puedes agregar la lógica para generar las cuotas del estudiante con el rut proporcionado
        // Luego, redirige a la página o realiza las acciones que sean necesarias
        // Por ejemplo, redirigir a una página de éxito o mostrar un mensaje
        return "gen_cuotas"; // Reemplaza "ruta-de-destino" con la URL adecuada
    }


    @GetMapping("/newInstallments/{rut}")
    public String generarCuotas(
            @PathVariable String rut,
            @RequestParam("cantidadCuotas") int cantidadCuotas
    ) {
        // En este punto, el valor de rut contiene "26.617.694-5" como se encuentra en la URL
        // Puedes tratar de procesar el valor de rut para quitar los guiones, si es necesario

        // Luego puedes utilizar los valores de rut y cantidadCuotas en tu lógica
        installmentService.generarCuotas(rut, cantidadCuotas);
        return "redirect:/students"; // Cambia "vistaResultado" por el nombre de tu vista de resultado
    }

    @GetMapping("/Generate-spreadsheet/{rut}")
    public String generarPlanilla(
            @PathVariable String rut
    ) {
        // En este punto, el valor de rut contiene "26.617.694-5" como se encuentra en la URL
        // Puedes tratar de procesar el valor de rut para quitar los guiones, si es necesario

        // Luego puedes utilizar los valores de rut y cantidadCuotas en tu lógica
        installmentService.generateSpreadsheet(rut);
        return "redirect:/students"; // Cambia "vistaResultado" por el nombre de tu vista de resultado
    }
    

    @PostMapping("/installment/save/")
    public InstallmentEntity guardar(@RequestBody InstallmentEntity installmentEntityNuevo){
        return installmentService.saveInstallment(installmentEntityNuevo);

    }



    @GetMapping("/listar-cuotas/{rut}")
    public String listar(Model model, @PathVariable("rut") String rut) {
        ArrayList<InstallmentEntity> installments = installmentService.getAllByRut(rut);
        model.addAttribute("installments",installments);
        return "list-installments";
    }





}