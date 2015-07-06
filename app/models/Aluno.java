package models;

import play.data.validation.Constraints;
import validator.FieldUnique;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Domingos Junior on 29/05/2015.
 */
@Entity
public class Aluno {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String nome;

    @Constraints.Required
    @FieldUnique(classe = Aluno.class, campo = "matricula")
    public String matricula;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Usuario usuario;

    @ManyToOne(optional=true)
    public Projeto projeto;

    @OneToMany(mappedBy = "aluno")
    public List<Arquivo> arquivos;
}
