package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ExamRepository extends CrudRepository<ExamEntity, Long> {

    public ArrayList<ExamEntity> findByRut(String rut);


}