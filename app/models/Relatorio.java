package models;

import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Valid;

/**
 * Created by Domingos Junior on 24/06/2015.
 */
@Entity
public class Relatorio {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public Integer quantidade;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String descricao;

    @Constraints.Required
    public DateTime dataDeEntrega;

    @Enumerated(EnumType.STRING)
    public StatusRelatorio statusRelatorio;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Anexo anexo;
}
