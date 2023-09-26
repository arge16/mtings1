
package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.InstallmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface InstallmentRepository extends CrudRepository<InstallmentEntity, Long>{

    public ArrayList<InstallmentEntity> findByRut(String rut);
    public boolean existsByRut(String rut);
}

