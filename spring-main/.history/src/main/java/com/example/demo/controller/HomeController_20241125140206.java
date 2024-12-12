package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Annonce;
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
    @GetMapping("/")
public String afficherAccueil(
        @RequestParam(value = "prixMin", required = false) Double prixMin,
        @RequestParam(value = "prixMax", required = false) Double prixMax,
        @RequestParam(value = "localisation", required = false) String localisation,
        Model model) {

    // Filtrage des annonces selon les critères
    List<Annonce> annonces;
    if (prixMin != null || prixMax != null || (localisation != null && !localisation.isEmpty())) {
        annonces = annonceRepository.findByCriteria(prixMin, prixMax, localisation);
    } else {
        annonces = annonceRepository.findAll();
    }

    model.addAttribute("annonces", annonces);
    return "home";
}

    
}
