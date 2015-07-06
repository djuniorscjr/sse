package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * Created by Domingos Junior on 30/06/2015.
 */
@Entity
public class Arquivo {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:mm:ss")
    public DateTime data;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Anexo anexo;

    @ManyToOne
    @PrimaryKeyJoinColumn
    public Relatorio relatorio;

    @ManyToOne
    @PrimaryKeyJoinColumn
    public Aluno aluno;
}
