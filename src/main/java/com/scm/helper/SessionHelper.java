package com.scm.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public static void removeMessage(){
        try{
            System.out.println("Removing message from session.......");
       // HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        
    
    HttpSession session =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
       System.out.println("session data::::"+ session);    
        session.removeAttribute("message");
        }
        catch(Exception e){
            System.out.println("error in sessionhelper:::"+e);
            e.printStackTrace();
        }
    }


}
