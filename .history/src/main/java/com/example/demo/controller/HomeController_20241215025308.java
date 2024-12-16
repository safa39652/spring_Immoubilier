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
    public String afficherAccueil(
            @RequestParam(value = "prixMin", required = false) Double prixMin,
            @RequestParam(value = "prixMax", required = false) Double prixMax,
            @RequestParam(value = "localisation", required = false) String localisation,
            @RequestParam(value = "categorie", required = false) String categorie,
            @RequestParam(value = "typeAnnonce", required = false) String typeAnnonce,
            Model model) {
    
        System.out.println("Prix Min: " + prixMin);
        System.out.println("Prix Max: " + prixMax);
        System.out.println("Localisation: " + localisation);
        System.out.println("Catégorie: " + categorie);
        System.out.println("Type Annonce: " + typeAnnonce);
    
        List<Annonce> annonces = annonceRepository.findAll().stream()
                .filter(a -> (prixMin == null || a.getPrix() >= prixMin))
                .filter(a -> (prixMax == null || a.getPrix() <= prixMax))
                .filter(a -> (localisation == null || a.getLocalisation().toLowerCase().contains(localisation.toLowerCase())))
               


                .collect(Collectors.toList());
    
        model.addAttribute("annonces", annonces);
        return "home";
    }
}