package co.edu.udea.tourofheroesapi.controller;

import co.edu.udea.tourofheroesapi.model.Hero;
import co.edu.udea.tourofheroesapi.service.HeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hero")
public class HeroController {

    private HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("/get-heroes")
    public ResponseEntity<List<Hero>> findAll(){
        var heroList = heroService.findAll();
        return ResponseEntity.ok(heroList);
    }

}
