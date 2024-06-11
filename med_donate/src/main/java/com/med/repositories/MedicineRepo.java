
package com.med.repositories;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.med.entities.Medicine;
import com.med.entities.User;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine, String> {
    // find the Medicine by user
    // custom finder method
    Page<Medicine> findByUser(User user, Pageable pageable);

    // custom query method
    @Query("SELECT c FROM Medicine c WHERE c.user.id = :userId")
    List<Medicine> findByUserId(@Param("userId") String userId);

    Page<Medicine> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);

    Page<Medicine> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);

    Page<Medicine> findByUserAndPhoneNumberContaining(User user, String phonekeyword, Pageable pageable);

}
