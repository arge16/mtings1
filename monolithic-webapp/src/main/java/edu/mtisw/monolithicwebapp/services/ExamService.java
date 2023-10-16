package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import edu.mtisw.monolithicwebapp.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Service
public class ExamService {
    @Autowired
    ExamRepository examRepository;

    private final Logger logg = LoggerFactory.getLogger(ExamService.class);

    public ArrayList<ExamEntity> getExams(){
        return (ArrayList<ExamEntity>) examRepository.findAll();
    }

   public void saveExam(ExamEntity exam) {
       examRepository.save(exam);
   }

    public Optional<ExamEntity> getById(Long id){
        return examRepository.findById(id);
    }

    public ArrayList<ExamEntity> getAllByRut(String rut){
        return examRepository.findByRut(rut);
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        //examRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public void guardarDataDB(String fecha, String rut, String score){
        ExamEntity newData = new ExamEntity();
        newData.setDate_of_exam(fecha);
        newData.setRut(rut);
        newData.setScore(Integer.parseInt(score));
        saveExam(newData);
    }
    public void eliminarData(ExamEntity exam){
        examRepository.delete(exam);
    }

}

