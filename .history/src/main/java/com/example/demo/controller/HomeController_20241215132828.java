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
    public String rechercheAnnonces(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            @RequestParam(required = false) String localisation,
            Model model
    ) {
        List<Annonce> annonces = annonceRepository.rechercherAnnonces(categorie, prixMin, prixMax, localisation);
        model.addAttribute("annonces", annonces);

    return "home";
}


}