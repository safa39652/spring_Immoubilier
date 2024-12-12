package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.AnnonceRepository;


@Controller

@RequestMapping("/")
public class HomeController {
 



    @Autowired
    private AnnonceRepository annonceRepository;

    @GetMapping("/")
    public String afficherAccueil(Model model) {
        model.addAttribute("annonces", annonceRepository.findAll());
        return "home"; 
    }
    
}
