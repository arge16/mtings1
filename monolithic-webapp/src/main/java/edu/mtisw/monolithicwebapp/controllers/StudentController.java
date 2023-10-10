
package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.InstallmentEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping
public class StudentController {
    @Autowired
	StudentService studentService;

    @GetMapping("/students")
	public String list(Model model) {
    	ArrayList<StudentEntity>students=studentService.getStudents();
    	model.addAttribute("students",students);
		return "index";
	}



	@PostMapping("/student/save/")
	public StudentEntity save(@RequestBody StudentEntity studentEntityNuevo){
		return studentService.saveStudent(studentEntityNuevo);
	}
	@GetMapping("/new-student")
	public String student(){
		return "new-student";
	}

	@PostMapping("/new-student")
	public String newStudent(@RequestParam("rut") String rut,
							 @RequestParam("name") String name,
							 @RequestParam("lastname") String lastname,
							 @RequestParam("birthdate") String birthdate,
							 @RequestParam("graduation_year") int graduation_year,
							 @RequestParam("school") String school,
							 @RequestParam("school_type") String school_type){

		studentService.saveStudentData(rut, name, lastname, birthdate, graduation_year, school, school_type);
		return "redirect:/new-student";
		//Redirect redirecciona un controlador
		//return retorna una vista
	}
}