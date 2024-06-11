
package com.med.services.impl;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.med.entities.RequestMedicine;
import com.med.repositories.RequestMedicineRepo;
import com.med.services.RequestMedicineService;

@Service
public class RequestMedicineServiceImpl implements RequestMedicineService

{

    @Autowired
    private RequestMedicineRepo requestMedicineRepo;

    @Override
    public RequestMedicine save(RequestMedicine requestMedicine) {
        String medicineId = UUID.randomUUID().toString();
        requestMedicine.setId(medicineId);
        return requestMedicineRepo.save(requestMedicine);
    }

   
}
