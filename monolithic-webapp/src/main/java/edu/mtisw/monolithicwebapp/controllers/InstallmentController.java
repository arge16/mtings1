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

    @GetMapping ("/mark-paid/{id}")
    public String markPaid(@PathVariable("id") Long id) {
        // StudentEntity student = studentService.getByRut(rut);
        installmentService.markPaid(id);

        return "redirect:/students"; // Reemplaza "ruta-de-destino" con la URL adecuada
    }



    @GetMapping("/gen-cuotas")
    public String student(){
        return "gen_cuotas";
    }


    @GetMapping("/generar-cuotas")
    public String generarCuotas(@PathVariable("rut") String rut) {
        StudentEntity student = studentService.getByRut(rut);

            installmentService.generarCuotas(rut);
        // Aquí puedes agregar la lógica para generar las cuotas del estudiante con el rut proporcionado
        // Luego, redirige a la página o realiza las acciones que sean necesarias
        // Por ejemplo, redirigir a una página de éxito o mostrar un mensaje
        return "redirect:/students"; // Reemplaza "ruta-de-destino" con la URL adecuada
    }

    @GetMapping("/listar-cuotas/{rut}")
    public String listar(Model model, @PathVariable("rut") String rut) {
        ArrayList<InstallmentEntity> installments=installmentService.getByRut(rut);
        model.addAttribute("installments",installments);
        return "list-installments";
    }



}