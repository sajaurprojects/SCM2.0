package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.helper.ResouceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
  private  ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        

       String contactId = UUID.randomUUID().toString();
  
       System.out.println("contact id::::::::::   "+contactId);
       
       contact.setId(contactId);

       System.out.println(contact.getId());

       return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
      

        return null;
    
    }

    @Override
    public List<Contact> getAllContact() {
    
        return contactRepo.findAll();
    }

   

    // @Override
    public void delete(String id) {
       
        var contactId = contactRepo.findById(id).orElseThrow(()-> new ResouceNotFoundException("Contact not found with given id: "+id));
    
        contactRepo.delete(contactId);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        return null;
    }

    @Override
    public List<Contact> getUserById(String userId) {
       
        return contactRepo.findByUserId(userId);

    }

    @Override
    public Contact getById(String id) {
        
        return contactRepo.findById(id).orElseThrow(()-> new ResouceNotFoundException("contact not found"));
    }

   
}

   

