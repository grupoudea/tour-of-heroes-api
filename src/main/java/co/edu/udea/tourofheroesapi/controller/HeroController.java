package co.edu.udea.tourofheroesapi.controller;

import co.edu.udea.tourofheroesapi.model.Hero;
import co.edu.udea.tourofheroesapi.service.HeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hero")
public class HeroController {

    private final Logger log = LoggerFactory.getLogger(HeroController.class);

    private HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Hero> getHero(@PathVariable Integer id){
        log.info("Rest request buscar heroe por id: "+ id);
        var hero = heroService.findById(id);

        return ResponseEntity.ok(hero);
    }
    @GetMapping("/get-heroes")
    public ResponseEntity<List<Hero>> findAll(){
        var heroList = heroService.findAll();
        return ResponseEntity.ok(heroList);
    }

}
