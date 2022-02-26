package com.ravindra.repo;

import com.ravindra.entity.AviationEntity;
import com.ravindra.util.QueryConstants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AviationRepo extends CrudRepository<AviationEntity, Long> {

    @Query(value = QueryConstants.AVIATION_MILATARY_LANDING, nativeQuery = true)
    List<AviationEntity> findByMilataryLanding();
}