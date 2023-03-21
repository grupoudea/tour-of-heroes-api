package co.edu.udea.tourofheroesapi.controller;

import co.edu.udea.tourofheroesapi.dto.HeroDto;
import co.edu.udea.tourofheroesapi.model.Hero;
import co.edu.udea.tourofheroesapi.service.HeroService;
import co.edu.udea.tourofheroesapi.util.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<StandardResponse<List<Hero>>> findAll() {
        var heroList = heroService.findAll();
        return ResponseEntity.ok(new StandardResponse(StandardResponse.StatusStandardResponse.OK, heroList));
    }

    @PostMapping
    public ResponseEntity<StandardResponse<Hero>> addHero(@RequestBody HeroDto heroDto){
        Hero heroOne = new Hero();
        heroOne.setName(heroDto.getName());
        var hero1 = heroService.createHero(heroOne);
        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK, "hero.create.ok", hero1));
    }

    @PutMapping
    public ResponseEntity<StandardResponse<Hero>> editHero(@RequestBody HeroDto heroDto) {
        Hero heroOne = new Hero();
        heroOne.setName(heroDto.getName());
        heroOne.setId(heroDto.getId());
        var hero1 = heroService.updateHero(heroOne);

        return ResponseEntity.ok(new StandardResponse<>(StandardResponse.StatusStandardResponse.OK, "hero.updated.ok", hero1));
    }



}
