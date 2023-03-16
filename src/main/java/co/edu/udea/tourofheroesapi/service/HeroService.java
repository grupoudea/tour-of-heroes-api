package co.edu.udea.tourofheroesapi.service;

import co.edu.udea.tourofheroesapi.model.Hero;
import co.edu.udea.tourofheroesapi.repository.HeroRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class HeroService {
    private HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Hero findById(Integer id) {
        if (Objects.isNull(id)) {
            System.out.println("id null");
        }
        return heroRepository.findById(id).orElseThrow(()-> new RuntimeException("DATA_NOT_FOUND"));
    }

    public List<Hero> findAll(){

        var heroList = heroRepository.findAll();
        if (heroList.isEmpty()) {
            System.out.printf("EMpty list");
        }
        return heroList;

    }

    public Hero createHero(Hero hero) {

        if (Objects.nonNull(hero.getId())) {
            Optional<Hero> heroOptional = heroRepository.findById(hero.getId());
            if (heroOptional.isPresent()) {
                System.out.println("throw ex data duplicate");
            }
        }

        try {
            return heroRepository.save(hero);
        } catch (DataIntegrityViolationException e) {
            System.out.println("throw data constraint violation ex");

        }
        return new Hero();
    }


}
