
package com.med.services.impl;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.med.entities.Medicine;
import com.med.entities.User;
import com.med.helpers.ResourceNotFoundException;
import com.med.repositories.MedicineRepo;
import com.med.services.MedicineService;

@Service
public class MedicineServiceImpl implements MedicineService

{

    @Autowired
    private MedicineRepo medicineRepo;

    @Override
    public Medicine save(Medicine medicine) {
// generates a universally unique identifier (UUID) 
        String medicineId = UUID.randomUUID().toString();
        medicine.setId(medicineId);
        return medicineRepo.save(medicine);

    }

    @Override
    public Medicine update(Medicine medicine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Medicine> getAll() {
        return medicineRepo.findAll();
    }

    @Override
    public Medicine getById(String id) {
        return medicineRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with given id " + id));
    }

    @Override
    public void delete(String id) {
        var medicine = medicineRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with given id " + id));
        medicineRepo.delete(medicine);

    }

    @Override
    public List<Medicine> getByUserId(String userId) {
        return medicineRepo.findByUserId(userId);

    }

    @Override
    public Page<Medicine> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return medicineRepo.findByUser(user, pageable);

    }

    @Override
    public Page<Medicine> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return medicineRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Medicine> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,
            User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return medicineRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Medicine> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return medicineRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }

}
