package models;


import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;
import validator.FieldUnique;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;

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

    @Formats.DateTime(pattern = "dd/MM/yyyy hh:mm:ss")
    public DateTime dataDeCadastro;

    @Enumerated(EnumType.STRING)
    public Permissao permissao;

    @OneToOne(mappedBy = "usuario")
    public Aluno aluno;

    @OneToOne(mappedBy = "usuario")
    public Professor professor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    public List<SituacaoRelatorio> situacoesRelatorio;


    @OneToMany(mappedBy = "usuario")
    public List<SituacaoProjeto> situacaoProjetos;
}
