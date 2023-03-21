package co.edu.udea.tourofheroesapi.repository;

import co.edu.udea.tourofheroesapi.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeroRepository extends JpaRepository<Hero, Integer> {

    List<Hero> findByNameContains(String name);
}
