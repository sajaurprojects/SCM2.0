package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;


@RequestMapping("/user/contacts")
@Controller
public class ContactController {

    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
@Autowired
    private ImageService imageService;

    @RequestMapping("/add")
    public String addContactView(Model model){

        ContactForm contactForm = new ContactForm();
       // contactForm.setName("Arman Khan");
       
        model.addAttribute("contactForm",contactForm);
        

        return "/user/add_contact";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,Authentication authentication,HttpSession session) {
       
        //Process form data



        //validat form
        if(result.hasErrors()){

            result.getAllErrors().forEach(errors-> logger.info(errors.toString()));

            session.setAttribute("message", Message.builder()
            .content("Please correct following errors")
            .type(MessageType.red)
            .build());
            return "/user/add_contact";
        }
        String userName= Helper.getEmailOfLoggedInUser(authentication);
        //form---->contact
        User user= userService.getUserByEmail(userName);

        //process the contact picture
        //proess image

        //upload karne ka code

        String fileName = UUID.randomUUID().toString();

       String fileURL = imageService.uploadImage(contactForm.getContactImage(),fileName);


        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkdin(contactForm.getLinkdin());
        contact.setWebsiteLinks(contactForm.getWebsiteLinks());
        contact.setFavourite(contactForm.isFavourite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setEmail(contactForm.getEmail());
        contact.setUser(user);
       contact.setPictures(fileURL);
        contact.setCloudnaryImagePublicId(fileName);
       contactService.save(contact);
        System.out.println(contactForm);

//Set message to display

session.setAttribute("message", "New Contact is added succesfully");

session.setAttribute("message", Message.builder()
.content("New Contact is added succesfully")
.type(MessageType.green)
.build());

        return "redirect:/user/contacts/add";
    }
    

}
