package co.edu.udea.tourofheroesapi.repository;

import co.edu.udea.tourofheroesapi.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Integer> {
}
