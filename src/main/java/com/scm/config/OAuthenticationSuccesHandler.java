package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthenticationSuccesHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthenticationSuccesHandler.class);
   @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
                logger.info("OAuthenticationSuccesHandler");

                //Identify provider first to login

              var oauth2AuthenticationToken =  (OAuth2AuthenticationToken)authentication;
              String oauth2AuthenticationTokenId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

              logger.info("Authorizetoken id::::"+oauth2AuthenticationTokenId);

              var oAuth2User = (DefaultOAuth2User)authentication.getPrincipal();

              oAuth2User.getAttributes().forEach((key,value)->{

                logger.info("{} =>{}",key,value);
              });

              User user = new User();

              user.setUserId(UUID.randomUUID().toString());
              user.setRoleList(List.of(AppConstants.ROLE_USER));
              user.setEmailVerified(true);
              user.setEnabled(true);
              user.setPassword("dummy");


              if(oauth2AuthenticationTokenId.equalsIgnoreCase("google")){
                 //google
                //google attributes

                user.setEmail(oAuth2User.getAttribute("email").toString());
                user.setProfilePic(oAuth2User.getAttribute("picture").toString());
                user.setName(oAuth2User.getAttribute("name").toString());
                user.setAbout(oAuth2User.getName());
                user.setProvider(Providers.GOOGLE);
                user.setAbout("This account is sign in by google account.");

              
                

              }else if(oauth2AuthenticationTokenId.equalsIgnoreCase("github")){

                //github
                //github attributes
                String email = oAuth2User.getAttribute("email") !=null ? oAuth2User.getAttribute("email").toString() 
                : oAuth2User.getAttribute("login").toString() + "@gmail.com";

                String picture = oAuth2User.getAttribute("avatar_url").toString();
                String name = oAuth2User.getAttribute("login").toString();
                String providerUserId = oAuth2User.getName();

                user.setEmail(email);
                user.setProfilePic(picture);
                user.setName(name);
                user.setProviderUserId(providerUserId);
                user.setProvider(Providers.GITHUB);
                user.setAbout("This account is sign in by github account.");
              }
              else{
                logger.info("OAuthenticationSuccesHandler::: unknown provider.");
              }
              //save user in DB
              User user2= userRepo.findByEmail(user.getEmail()).orElse(null);

                 if(user2==null){
                  
                    userRepo.save(user);
  
                         logger.info("User saved with email id :" + user.getEmail());
                
                 }
                 new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

                //facebook
                //facebook attributes


            //    DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();

            //    logger.info(user.getName());

            //    user.getAttributes().forEach((key,value)->{

            //     logger.info("{} =>{}",key,value);
                
            //    });
            //    logger.info(user.getAuthorities().toString());

            //    //Before redirection save data in DB by taking out from google account

            //    String email = user.getAttribute("email").toString();
            //    String name=user.getAttribute("name").toString();
            //    String picture = user.getAttribute("picture").toString();

            //    //create user and save in database

            //    User user1= new User();
            //    user1.setEmail(email);
            //    user1.setName(name);
            //    user1.setProfilePic(picture);
            //    user1.setPassword("password");
            //    user1.setUserId(UUID.randomUUID().toString());
            //    user1.setProvider(Providers.GOOGLE);
            //    user1.setEnabled(true);
            //    user1.setEmailVerified(true);
            //    user1.setProviderUserId(user.getName());
            //    user1.setRoleList(List.of(AppConstants.ROLE_USER));
            //    user1.setAbout("This account is created using google account....");

            //    User user2= userRepo.findByEmail(email).orElse(null);

            //    if(user2==null){
            //     userRepo.save(user1);

            //     logger.info("User saved with email id :" + email);
            //    }

           //    response.sendRedirect("/home");
              
    }



}
