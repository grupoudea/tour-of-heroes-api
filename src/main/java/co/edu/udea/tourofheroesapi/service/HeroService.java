package co.edu.udea.tourofheroesapi.service;

import co.edu.udea.tourofheroesapi.model.Hero;
import co.edu.udea.tourofheroesapi.repository.HeroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HeroService {
    private HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public List<Hero> findAll(){

        var heroList = heroRepository.findAll();
        if (heroList.isEmpty()) {
            System.out.printf("EMpty list");
        }
        return heroList;

    }
}
