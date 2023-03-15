package co.edu.udea.tourofheroesapi.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "heroes")
@Data
public class Hero {

    @Id
    @NonNull
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public Hero() {
    }
}
