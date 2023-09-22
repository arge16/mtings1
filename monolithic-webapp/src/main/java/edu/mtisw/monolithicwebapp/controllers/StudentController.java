
package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import edu.mtisw.monolithicwebapp.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

}