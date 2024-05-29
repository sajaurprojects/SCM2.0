package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {

    public User saveUser(User user);
    public Optional<User> getUserById(String id);
    public Optional<User>  updateUser(User user);
    public void deleteUser(String id);
    public boolean isUserExist(String emailId);
    public boolean isUserExistByEmail(String userName);
    public List<User>getAllUsers();
    public User getUserByEmail(String email);

    //we can add more method if required in business logic



}
