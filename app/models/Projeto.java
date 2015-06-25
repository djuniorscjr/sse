package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Domingos Junior on 24/06/2015.
 */
@Entity
public class Projeto {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String titulo;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String descricao;

    @Constraints.Required
    public Integer quantidadeMaxDeParticipantes;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Anexo anexo;

    @OneToMany(mappedBy = "projeto")
    public List<Aluno> alunos;

    @ManyToOne
    public Professor professor;

    @Enumerated(EnumType.STRING)
    public StatusProjeto statusProjeto;
}
