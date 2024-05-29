package com.scm.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.services.UserService;



@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    //user dashboard page



    @RequestMapping(value="/dashboard",method=RequestMethod.GET)
    public String userDashBoard() {
        return "user/dashboard";
    }
    
//user profile page
//Principal interface is used to get user data who is logged in
    @RequestMapping(value="/profile",method=RequestMethod.GET)
    public String userProfile( ) {

        return "user/profile";
    }
    //user add contact page
    //user view contact page
    //user edit contact page
    //user delete contact page
    //user search contact


}
