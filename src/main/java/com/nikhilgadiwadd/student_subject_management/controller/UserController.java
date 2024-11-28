package com.nikhilgadiwadd.student_subject_management.controller;


import com.nikhilgadiwadd.student_subject_management.entity.BlacklistedToken;
import com.nikhilgadiwadd.student_subject_management.jwt.*;
import com.nikhilgadiwadd.student_subject_management.repository.BlacklistedTokenRepository;
import com.nikhilgadiwadd.student_subject_management.service.StudentService;
import com.nikhilgadiwadd.student_subject_management.service.SubjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JdbcUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthTokenFilter authTokenFilter;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);
        logger.info("Signin successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        // Check if the user already exists
        if (userDetailsManager.userExists(registerRequest.getUsername())) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "User already exists");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        // Create a new user
        UserDetails newUser = User.withUsername(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(registerRequest.getRole())
                .build();

        // Save the user to the database
        userDetailsManager.createUser(newUser);

        Map<String, Object> map = new HashMap<>();
        map.put("message", "User registered successfully");
        map.put("status", true);
        logger.info("User registered successfully");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // Remove "Bearer " prefix
        } else {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header.");
        }

        BlacklistedToken blacklistedToken = new BlacklistedToken(jwt, LocalDateTime.now());
        blacklistedTokenRepository.save(blacklistedToken);

        return ResponseEntity.ok("User logged out successfully. Token has been invalidated.");
    }
}
