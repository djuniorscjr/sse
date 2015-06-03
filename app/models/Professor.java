package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * Created by Domingos Junior on 29/05/2015.
 */
@Entity
public class Professor {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String nome;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Usuario usuario;

}
