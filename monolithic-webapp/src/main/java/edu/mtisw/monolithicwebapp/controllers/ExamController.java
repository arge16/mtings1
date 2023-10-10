
package edu.mtisw.monolithicwebapp.controllers;
import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.services.ExamService;
import edu.mtisw.monolithicwebapp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class ExamController {

    @Autowired
    private ExamService examService;

    @GetMapping("/fileUpload")
    public String main() {
        return "fileUpload";
    }

    @PostMapping("/fileUpload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        examService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        examService.leerCsv("Examen.csv");
        return "redirect:/fileUpload";
    }

    @GetMapping("/fileInformation")
    public String listar(Model model) {
        ArrayList<ExamEntity> datas = examService.getExams();
        model.addAttribute("datas", datas);
        return "fileInformation";
    }
}



