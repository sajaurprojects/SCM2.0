package com.scm.helper;



import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;



public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){


        //to get email if user logged in

         // AuthenticationPrincipal principal = (AuthenticationPrincipal) authentication.getPrincipal();
         
         

          if(authentication instanceof OAuth2AuthenticationToken){

           var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;

           var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
           var oauth2User =  (OAuth2User)authentication.getPrincipal();

          String usrName ="";

                if(clientId.equalsIgnoreCase("google"))
                {

                    

                    //  //if we logged in with email and password then how we can get email of that particular user who is logged in.

                    System.out.println("Getting email form google client::::::::::::::;");

                  usrName = oauth2User.getAttribute("email").toString();

                  

                }

                else if(clientId.equalsIgnoreCase("github")){

                     // //if we logged in with github email and password then how we can get email of that particular user who is logged in
                         System.out.println("Getting email form github client::::::::::::::;");
                      usrName = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                        :oauth2User.getAttribute("login").toString() + "@gmail.com";
                 
                }

                return usrName;
            }
                
            else
                {

                   
                    String usName= authentication.getName();
                    System.out.println("Getting data from local DB::::::::::::::::"+usName);

                    return usName;
                }

          }
    
    }


