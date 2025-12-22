package com.example.demo.service;

import com.example.demo.model.UserAccount;
import java.util.List;

public interface UserAccountService {
    UserAccount createUser(UserAccount user);
    UserAccount authenticate(String email, String password);
    List<UserAccount> getAllUsers();
    UserAccount getUserById(Long id);
}
