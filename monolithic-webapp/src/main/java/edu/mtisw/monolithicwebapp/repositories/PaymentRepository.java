
package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.PaymentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long>{

    public PaymentEntity findByRut(String rut);
}

