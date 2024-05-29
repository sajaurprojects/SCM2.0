package com.scm.services;

import com.scm.entities.Contact;
import java.util.*;
 

public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contacts);

    List<Contact> getAllContact();

    Contact getById(String id);

   void delete(String id);

    //search Contact
    List<Contact> search(String name, String email, String phoneNumber);

    List<Contact> getUserById(String userId);


}
