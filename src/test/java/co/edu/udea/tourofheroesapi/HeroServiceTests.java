package co.edu.udea.tourofheroesapi;

import co.edu.udea.tourofheroesapi.exception.DataConstraintViolationException;
import co.edu.udea.tourofheroesapi.exception.DataDuplicatedException;
import co.edu.udea.tourofheroesapi.exception.LocalObjectNotFoundException;
import co.edu.udea.tourofheroesapi.model.Hero;
import co.edu.udea.tourofheroesapi.repository.HeroRepository;
import co.edu.udea.tourofheroesapi.service.HeroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
class HeroServiceTests {

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HeroService heroService;

    private Hero hero;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByIdSuccess() {

        // Arrange = preparar datos de prueba
        hero = new Hero();
        hero.setId(7);
        hero.setName("HULK BOOT");
        // Configuramos el mock para que devuelva un objeto Hero
        Mockito.when(heroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(hero));

        // Act = ejecuta prueba como tal pues
        Hero result = heroService.findById(1);

        // Assert = validaciones que quiero
        assertNotNull(result);
        assertEquals(7, result.getId());
        assertEquals("HULK BOOT", result.getName());
    }

    @Test()
    public void testFindByIdNotFound() {
        //Arrange
        Mockito.when(heroRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // Assert
        assertThrows(RuntimeException.class,
                () -> heroService.findById(1),
                "Unexpected exception type thrown");

    }

    @Test
    public void testFindByIdNullId() {
        assertThrows(LocalObjectNotFoundException.class, () -> {
            heroService.findById(null);
        });
    }


    @Test
    public void testCreateHeroSuccess() {

        // Arrange = preparar datos de prueba
        hero = new Hero();
        hero.setName("HULK BOOT");
        // Configuramos el mock para que devuelva un objeto Hero
        Mockito.when(heroRepository.save(any(Hero.class))).thenReturn((hero));

        // Act = ejecuta prueba
        Hero result = heroService.createHero(hero);

        // Assert = validaciones que quiero
        assertNotNull(result);
        assertEquals("HULK BOOT", result.getName());
    }

    @Test
    public void testCreateHeroWithDuplicateId() {

        // Arrange = preparar datos de prueba
        hero = new Hero();
        hero.setId(7);
        hero.setName("HULK BOOT");
        // Configuramos el mock para que devuelva un objeto Hero
        Mockito.when(heroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(hero));

        // Act and Assert
        assertThrows(DataDuplicatedException.class, ()-> {
            heroService.createHero(hero);
        });

    }

    @Test
    void testCreateNewHeroWithId() {
        // Arrange
        Hero hero = new Hero();
        hero.setId(1);
        hero.setName("Iron Man");

        Mockito.when(heroRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(heroRepository.save(Mockito.any(Hero.class))).thenReturn(hero);

        // Act
        Hero result = heroService.createHero(hero);

        // Assert

        // Verificamos que los metodos findByID Y save se llamen exactamente una sola vez
        Mockito.verify(heroRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(heroRepository, Mockito.times(1)).save(Mockito.any(Hero.class));
        assertNotNull(result.getId());
        assertEquals(1, result.getId());
        assertEquals("Iron Man", result.getName());

    }

    @Test
    public void testCreateHeroDataIntegrity() {

        // Arrange = preparar datos de prueba
        hero = new Hero();
        Mockito.when(heroRepository.save(Mockito.any(Hero.class)))
                .thenThrow(DataIntegrityViolationException.class);

        // Act and Assert
        assertThrows(DataConstraintViolationException.class, ()-> {
            heroService.createHero(hero);
        });

    }

    @Test
    @AfterTransaction
    public void testUpdateHeroSuccess() {

        // Arrange = preparar datos de prueba
        hero = new Hero();
        hero.setId(1);
        hero.setName("NO NAME");

        Hero updatedHero = new Hero();

        updatedHero.setId(1);
        updatedHero.setName("Iron man");

        Mockito.when(heroRepository.findById(anyInt())).thenReturn(Optional.of(hero));

        // Act = ejecuta prueba
        Hero result = heroService.updateHero(updatedHero);

        // Assert = validaciones que quiero
        assertNotNull(result);
        assertEquals("Iron man" , result.getName());
    }

    @Test
    public void testDeleteHeroSuccess() {

        // Arrange = preparar datos de prueba
        hero = new Hero();
        hero.setId(1);
        hero.setName("Iron man");
        Mockito.when(heroRepository.findById(anyInt())).thenReturn(Optional.of(hero));
        Mockito.doNothing().when(heroRepository).deleteById(anyInt());

        // Act = ejecuta prueba
        heroService.deleteHero(hero.getId());

        // Assert = validaciones que quiero
        Mockito.verify(heroRepository, Mockito.times(1)).deleteById(Mockito.anyInt());

    }




}
