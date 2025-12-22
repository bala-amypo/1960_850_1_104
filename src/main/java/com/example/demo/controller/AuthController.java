package com.example.demo.controller;

// import com.example.demo.dto.AuthRequest;
// import com.example.demo.dto.AuthResponse;
// import com.example.demo.dto.RegisterRequest;
// import com.example.demo.model.UserAccount;
// import com.example.demo.service.UserAccountService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/auth")
// @Tag(name = "Authentication", description = "User registration and login (no security yet)")
// public class AuthController {

//     private final UserAccountService userService;

//     public AuthController(UserAccountService userService) {
//         this.userService = userService;
//     }

//     @PostMapping("/register")
//     @Operation(summary = "Register new user (no JWT yet)")
//     public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
//         UserAccount user = new UserAccount();
//         user.setFullName(request.getFullName());
//         user.setEmail(request.getEmail());
//         // store raw password for now; hashing will be added when security is implemented
//         user.setPasswordHash(request.getPassword());
//         user.setRole(request.getRole());
//         user.setActive(true);

//         UserAccount created = userService.createUser(user);

//         // no token yet
//         return ResponseEntity.status(HttpStatus.CREATED)
//                 .body(new AuthResponse(null, created.getId(), created.getEmail(), created.getRole()));
//     }

//     @PostMapping("/login")
//     @Operation(summary = "Login user (no JWT yet)")
//     public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
//         UserAccount user = userService.authenticate(request.getEmail(), request.getPassword());
//         return ResponseEntity.ok(
//                 new AuthResponse(null, user.getId(), user.getEmail(), user.getRole())
//         );
//     }
// }
