package com.scm.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {


    private Logger logger = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;
    
    @ModelAttribute
    public void addLoggedInUseInfo(Model model ,Authentication authentication){

        if (authentication==null) {
            return;
        }

        

   System.out.println("Adding loggedIn info to user................");
   String userName = Helper.getEmailOfLoggedInUser(authentication);

   // String name= principal.getName();
   logger.info("User logged in:{}", userName);

   //DB se data ko fetch karne ke liye : Get user data :email,name,address from Db and show on page.

   User user= userService.getUserByEmail(userName);


   System.out.println(user);
   System.out.println("email ======  " +user.getEmail());
   System.out.println("Name:::: "+ user.getName());
 
 
    model.addAttribute("loggedInUser",user);

   
    }



}
