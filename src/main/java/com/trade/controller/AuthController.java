
package com.trade.controller;

import com.trade.config.JwtProvider;
import com.trade.modal.User;
import com.trade.repository.UserRepository;
import com.trade.response.AuthResponse;
import com.trade.service.CustomUserDetailsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // PasswordEncoder to compare the hashed passwords
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        // Check if email already exists
        List<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist != null) {
            throw new Exception("Email is already exists..");
        }

        // Create a new user and encode the password before saving
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt the password
        newUser.setFullName(user.getFullName());

        // Save user to the database
        User savedUser = userRepository.save(newUser);

        // Authenticate the user
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Generate JWT token
        String jwt = JwtProvider.generateToken(auth);

        // Prepare the response with the token
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Registration successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String userName = user.getEmail();
        String password = user.getPassword();
        
        System.out.println(user.getEmail()+"  : password :  " + user.getPassword());

        // Authenticate the user
        Authentication auth = authenticate(userName, password);
        System.out.println("genrate Token .....  : " + auth);
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Generate JWT token
        String jwt = JwtProvider.generateToken(auth);

        System.out.println("genrate Token .....  : " + jwt);
        // Prepare the response with the token
        AuthResponse res = new AuthResponse();
        System.out.println("Create res : " + res);
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login successful");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private Authentication authenticate(String userName, String password) {
        // Load user details from custom service
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        // If user not found, throw BadCredentialsException
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Compare the provided password with the encoded password
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // If authentication is successful, return the token
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

}

