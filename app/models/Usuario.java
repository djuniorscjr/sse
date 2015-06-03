package models;


import org.joda.time.DateTime;
import play.data.validation.Constraints;
import validator.FieldUnique;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Null;

/**
 * Created by Domingos Junior on 29/05/2015.
 */
@Entity
public class Usuario {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    @Constraints.Email
    @FieldUnique(classe = Usuario.class, campo = "email")
    public String email;
    @Constraints.Required
    public String senha;
    public String token;
    public Boolean ativo;

    public DateTime dataDeCadastro;

    @Enumerated(EnumType.STRING)
    public Permissao permissao;

    @OneToOne(mappedBy = "usuario")
    public Aluno aluno;

    @OneToOne(mappedBy = "usuario")
    public Professor professor;
}
