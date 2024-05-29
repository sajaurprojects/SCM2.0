package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.scm.entities.Contact;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String>{

    //find contact by user

    //custom finder method
    List<Contact> findByUser(User user);

    //custom Query method
    @Query("SELECT c from Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId( @Param("userId") String  userId);

}
