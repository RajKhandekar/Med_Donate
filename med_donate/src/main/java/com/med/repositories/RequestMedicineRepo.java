package com.med.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.med.entities.RequestMedicine;

@Repository
public interface RequestMedicineRepo extends JpaRepository<RequestMedicine,String> {
    
}
