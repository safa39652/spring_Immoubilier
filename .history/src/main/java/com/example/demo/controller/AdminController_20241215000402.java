package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Annonce;
import com.example.demo.repository.AnnonceRepository;
import com.example.demo.repository.BienRepository;

import jakarta.servlet.ServletContext;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private BienRepository bienRepository;

    @Autowired
  

    @GetMapping("/annonces")
    public String listerAnnonces(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "4") int size,
                                 Model model) {
        Page<Annonce> annoncesPage = annonceService.getAllAnnoncePagination(page, size);
        model.addAttribute("annonces", annoncesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", annoncesPage.getTotalPages());
        return "admin/liste_annonces";
    }


    @GetMapping("/annonces/ajouter")
    public String afficherFormAjout(Model model) {
        model.addAttribute("annonce", new Annonce());
        model.addAttribute("biens", bienRepository.findAll());
        return "admin/ajouter_annonce";
    }

    @PostMapping("/annonces/ajouter")
    public String ajouterAnnonce(@ModelAttribute Annonce annonce, 
                                 @RequestParam("photo") MultipartFile photo) {
        if (!photo.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/";
                Files.createDirectories(Paths.get(uploadDir));
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path path = Paths.get(uploadDir + fileName);
                Files.copy(photo.getInputStream(), path);
                annonce.setPhotoUrl(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/erreur";  
            }
        }
        annonceRepository.save(annonce);
        return "redirect:/admin/annonces";
    }

    @GetMapping("/annonces/modifier/{id}")
    public String afficherFormModifier(@PathVariable Long id, Model model) {
        Annonce annonce = annonceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Annonce non trouvée : " + id));
        model.addAttribute("annonce", annonce);
        return "admin/modifier_annonce";
    }

    @PostMapping("/annonces/modifier/{id}")
    public String mettreAJourAnnonce(@PathVariable Long id, @ModelAttribute Annonce annonce) {
        Annonce existingAnnonce = annonceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Annonce non trouvée : " + id));

        existingAnnonce.setTitre(annonce.getTitre());
        existingAnnonce.setDescription(annonce.getDescription());
        existingAnnonce.setPrix(annonce.getPrix());
        existingAnnonce.setLocalisation(annonce.getLocalisation());
        existingAnnonce.setSuperficie(annonce.getSuperficie());
        existingAnnonce.setNombrePieces(annonce.getNombrePieces());
        existingAnnonce.setCategorie(annonce.getCategorie());
        existingAnnonce.setContact(annonce.getContact());

        annonceRepository.save(existingAnnonce);
        return "redirect:/admin/annonces";
    }

    @GetMapping("/annonces/supprimer/{id}")
    public String supprimerAnnonce(@PathVariable Long id) {
        annonceRepository.deleteById(id);
        return "redirect:/admin/annonces";
    }
}
