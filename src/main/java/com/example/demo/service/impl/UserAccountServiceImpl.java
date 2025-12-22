// package com.example.demo.service.impl;

// import com.example.demo.model.UserAccount;
// import com.example.demo.repository.UserAccountRepository;
// import com.example.demo.service.UserAccountService;
// import com.example.demo.exception.BadRequestException;
// import org.springframework.stereotype.Service;
// import java.util.Optional;

// @Service
// public class UserAccountServiceImpl implements UserAccountService {
//     private final UserAccountRepository repository;
//     public UserAccountServiceImpl(UserAccountRepository repository) { this.repository = repository; }

//     @Override
//     public UserAccount createUser(UserAccount user) {
//         if (repository.findByEmail(user.getEmail()).isPresent()) {
//             throw new BadRequestException("Email " + user.getEmail() + " already exists");
//         }
//         return repository.save(user);
//     }

//     @Override
//     public Optional<UserAccount> findByEmail(String email) { return repository.findByEmail(email); }

//     @Override
//     public UserAccount authenticate(String email, String password) {
//         UserAccount user = repository.findByEmail(email)
//                 .orElseThrow(() -> new BadRequestException("Invalid email or password"));
//         if (!user.getPassword().equals(password) || !user.getActive()) {
//             throw new BadRequestException("Invalid email or password");
//         }
//         return user;
//     }
// }
