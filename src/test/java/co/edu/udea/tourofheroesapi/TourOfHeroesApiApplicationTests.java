package co.edu.udea.tourofheroesapi;

import co.edu.udea.tourofheroesapi.model.Hero;
import co.edu.udea.tourofheroesapi.repository.HeroRepository;
import co.edu.udea.tourofheroesapi.service.HeroService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourOfHeroesApiApplicationTests {


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



}
