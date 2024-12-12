package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

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
public String afficherAnnonces(
    @RequestParam(required = false) Double prixMin,
    @RequestParam(required = false) Double prixMax,
    @RequestParam(required = false) String localisation,
    @RequestParam(required = false) String type, // Nouveau paramètre pour le type
    Model model) {
    
    List<Annonce> annoncesFiltrees = annonceRepository.findAll().stream()
        .filter(a -> (prixMin == null || a.getPrix() >= prixMin))
        .filter(a -> (prixMax == null || a.getPrix() <= prixMax))
        .filter(a -> (localisation == null || localisation.isEmpty() || a.getLocalisation().equalsIgnoreCase(localisation)))
        .filter(a -> (type == null || type.isEmpty() || a.getCategorie().contains(type))) // Filtrage par type
        .collect(Collectors.toList());
    
    model.addAttribute("annonces", annoncesFiltrees);
    return "accueil"; // Retourne la page d'accueil avec les annonces filtrées
}


}