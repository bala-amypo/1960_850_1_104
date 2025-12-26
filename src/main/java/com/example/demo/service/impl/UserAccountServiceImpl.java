package com.example.demo.service.impl;


// USER ACCOUNT SERVICE IMPLEMENTATION COMMENTED OUT
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.UserAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountServiceImpl(UserAccountRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount createUser(UserAccount user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return repository.save(user);
    }

    @Override
    public UserAccount authenticate(String email, String password) {
        UserAccount user = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getActive()) {
            throw new BadRequestException("User is not active");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadRequestException("Invalid credentials");
        }

        return user;
    }

    @Override
    public List<UserAccount> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public UserAccount getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}


// // Simple implementation without security
// import com.example.demo.exception.BadRequestException;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.model.UserAccount;
// import com.example.demo.repository.UserAccountRepository;
// import com.example.demo.service.UserAccountService;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class UserAccountServiceImpl implements UserAccountService {

//     private final UserAccountRepository repository;

//     public UserAccountServiceImpl(UserAccountRepository repository) {
//         this.repository = repository;
//     }

//     @Override
//     public UserAccount createUser(UserAccount user) {
//         if (repository.findByEmail(user.getEmail()).isPresent()) {
//             throw new BadRequestException("Email already exists");
//         }
//         return repository.save(user);
//     }

//     @Override
//     public UserAccount authenticate(String email, String password) {
//         UserAccount user = repository.findByEmail(email)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//         return user;
//     }

//     @Override
//     public List<UserAccount> getAllUsers() {
//         return repository.findAll();
//     }

//     @Override
//     public UserAccount getUserById(Long id) {
//         return repository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//     }
// }
