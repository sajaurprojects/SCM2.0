package com.scm.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.helper.ResouceNotFoundException;
import com.scm.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService 
{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

  
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
    
//generate user id to generate
String userId= UUID.randomUUID().toString();
  user.setUserId(userId);

  //Encode password
    //user.setPassword(userId);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    //set user role
    
    user.setRoleList(List.of(AppConstants.ROLE_USER));
    logger.info(user.getProvider().toString());
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
  
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       //user coming from db
        User foundUser = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResouceNotFoundException("User not found"));
        //update foundUser from user which are already in db
        foundUser.setName(user.getName());
        foundUser.setEmail(user.getEmail());
        foundUser.setPassword(user.getPassword());
        foundUser.setAbout(user.getAbout());
        foundUser.setPhoneNumber(user.getPhoneNumber());
        foundUser.setProfilePic(user.getProfilePic());
        foundUser.setEnabled(user.isEnabled());
        foundUser.setEmailVerified(user.isEmailVerified());
        foundUser.setPhoneVerified(user.isPhoneVerified());
        foundUser.setProvider(user.getProvider());
        foundUser.setProviderUserId(user.getProviderUserId());
        //save user in DB
       User savedUser = userRepo.save(foundUser);
        return Optional.ofNullable(savedUser);

    }
    

    @Override
    public void deleteUser(String id) {
        
      User user2 =  userRepo.findById(id).orElseThrow(()-> new ResouceNotFoundException("User not found"));
        userRepo.delete(user2);
        
    }

    @Override
    public List<User> getAllUsers() {
        
        return userRepo.findAll();
    }

    

    @Override
    public boolean isUserExist(String userId) {
       User user2=userRepo.findById(userId).orElse(null);
        return user2!=null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
      User user =  userRepo.findByEmail(email).orElseThrow(null);
        return user!=null ? true :false;
    }

    @Override
    public User getUserByEmail(String email) {
        
        return userRepo.findByEmail(email).orElse(null);
    }

   
   

}
