package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.ExamEntity;
import edu.mtisw.monolithicwebapp.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<ExamEntity, Long> {

    public ExamEntity findByRut(String rut);
}