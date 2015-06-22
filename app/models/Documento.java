package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * Created by Domingos Junior on 21/06/2015.
 */
@Entity
public class Documento {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String titulo;
    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String descricao;

    public Integer numero;
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Anexo anexo;
}
