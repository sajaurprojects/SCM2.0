package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){

        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model){

        //Sending data to view
        model.addAttribute("name", "Afsheen Fatima");
        model.addAttribute("address", "Motipur , Bihar");
        model.addAttribute("age", "2 years");
        model.addAttribute("linkpage", "www.google.com");

        System.out.println("Home page handler...........");
        return"home";

    }

    //about route

    @RequestMapping("/about")
    public String about() {
        System.out.println("About page loading.......");
        return "/about";
    }
    

    //services route
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services page loading......");
        return "/services";
    }

    //for showing login page
    @GetMapping("/login")
    public String signIn() {
        return new String("/login");
    }
   
    //for showing signup/registration page
    @GetMapping("/signup")
    public String signUp(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        //set default data
        
    //     userForm.setName("Arman Khan");
    //     userForm.setPhoneNumber("987654321");
    //     userForm.setEmail("srk@gmail.com");
    //     userForm.setPassword("12345");
    //    userForm.setAbout("hey where are u");
        return new String("/signup");
    }

    @GetMapping("/contacts")
    public String contacts() {
        return new String("/contacts");
    }
   
    //regestring processing to submit data to DB
    @RequestMapping(value = "/do-register", method=RequestMethod.POST)
    public String formRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult, HttpSession session){
         System.out.println(userForm);

         //validae form data

         if(bindingResult.hasErrors()){
            return "signup";
         }

        //fetch form data 
        //create class UserForm

        //save to DB
    //    User usr = User.builder()
    //    .name(userForm.getName())
    //    .about(userForm.getAbout())
    //    .email(userForm.getEmail())
    //    .password(userForm.getPassword())
    //    .phoneNumber(userForm.getPhoneNumber())
    //    .profilePic("https://img.freepik.com/free-vector/illustration-businessman_53876-5856.jpg?size=626&ext=jpg&ga=GA1.1.767189808.1716006128&semt=ais_user_b")
    //    .build();

    User usr = new User();
    usr.setName(userForm.getName());
    usr.setAbout(userForm.getAbout());
    usr.setEmail(userForm.getEmail());
    usr.setPassword(userForm.getPassword());
    usr.setPhoneNumber(userForm.getPhoneNumber());
    usr.setProfilePic("https://img.freepik.com/free-vector/illustration-businessman_53876-5856.jpg?size=626&ext=jpg&ga=GA1.1.767189808.1716006128&semt=ais_user_b");

       User savedUser = userService.saveUser(usr);

        System.out.println("User saved...........");

       Message msg= Message.builder().content("Registration Successfull").type(MessageType.green).build();
        //show message in form="Registration successfull."
        //add message with the help of session
        session.setAttribute("message",msg);

        return "redirect:/signup";
    }
   


}
