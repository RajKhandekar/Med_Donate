
package com.med.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.med.entities.Medicine;
import com.med.entities.User;

public interface MedicineService {
    // save Medicines
    Medicine save(Medicine medicine);

    // update Medicine
    Medicine update(Medicine medicine);

    // get Medicines
    List<Medicine> getAll();

    // get Medicine by id

    Medicine getById(String id);

    // delete Medicine

    void delete(String id);

    // search Medicine
    Page<Medicine> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    Page<Medicine> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    Page<Medicine> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            User user);

    // get Medicines by userId
    List<Medicine> getByUserId(String userId);

    Page<Medicine> getByUser(User user, int page, int size, String sortField, String sortDirection);

}
