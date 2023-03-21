package co.edu.udea.tourofheroesapi.service;

import co.edu.udea.tourofheroesapi.exception.DataConstraintViolationException;
import co.edu.udea.tourofheroesapi.exception.DataDuplicatedException;
import co.edu.udea.tourofheroesapi.exception.DataNotFoundException;
import co.edu.udea.tourofheroesapi.exception.LocalObjectNotFoundException;
import co.edu.udea.tourofheroesapi.model.Hero;
import co.edu.udea.tourofheroesapi.repository.HeroRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class HeroService {
    private HeroRepository heroRepository;

    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Hero findById(Integer id) {
        if (Objects.isNull(id)) {
            throw new LocalObjectNotFoundException("ex.hero.object_not_found");
        }
        return heroRepository.findById(id).orElseThrow(()-> new DataNotFoundException("ex.data_not_found"));
    }

    public List<Hero> findAll(){

        var heroList = heroRepository.findAll();
        if (heroList.isEmpty()) {
            throw new DataNotFoundException("ex.hero.data_not_found");
        }
        return heroList;

    }

    public Hero createHero(Hero hero) {
        if (Objects.nonNull(hero.getId())) {
            var heroOptional = heroRepository.findById(hero.getId());
            if (heroOptional.isPresent()) {
                throw new DataDuplicatedException("ex.hero.data_duplicated");
            }
        }

        try {
            return heroRepository.save(hero);
        } catch (DataIntegrityViolationException e) {
            throw new DataConstraintViolationException("ex.hero.data_constraint_violation");
        }
    }

    public Hero updateHero(Hero hero) {
        if (Objects.isNull(hero.getId())){
            throw new LocalObjectNotFoundException("ex.hero.object_not_found");
        }

        var heroTransaction = heroRepository.findById(hero.getId())
                .orElseThrow(() -> new DataNotFoundException("ex.hero.data_not_found") );

        heroTransaction.setName(hero.getName());

        return heroTransaction;

    }

    public void deleteHero(Integer heroId) {
        if(Objects.nonNull(heroId)) {
            var heroOptional = heroRepository.findById(heroId);
            if (!heroOptional.isPresent()) {
                throw new DataNotFoundException("ex.hero.data_not_found");
            }
        }

        heroRepository.deleteById(heroId);
    }

    public List<Hero> searchHero(String filter) {

        if(Objects.isNull(filter)){
            throw new LocalObjectNotFoundException("ex.hero.object_not_found");
        }
        var heroList = heroRepository.findByNameContains(filter);

        return heroList;

    }


}
