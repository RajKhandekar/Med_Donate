package com.med.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.med.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

   // methods like save,load,create ...etc already there
   

    // extra methods db relatedoperations
    // custom query methods
    // custom finder methods

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

}
