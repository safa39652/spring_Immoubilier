package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecuredPasswordGenerator {

    public static void main(String[] args) {
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
     
        String rawPassword = "password";
        
       
        String encodedPassword = encoder.encode(rawPassword);
    
        System.out.println("Encoded Password: " + encodedPassword);
    }
}
