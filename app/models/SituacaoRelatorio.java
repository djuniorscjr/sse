package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;

/**
 * Created by Domingos Junior on 30/06/2015.
 */
@Entity
public class SituacaoRelatorio {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    @Formats.DateTime(pattern = "dd/MM/yyyy hh:mm:ss")
    public DateTime data;

    @Enumerated(EnumType.STRING)
    public StatusRelatorio statusRelatorio;

    @ManyToOne
    public Relatorio relatorio;

    @ManyToOne
    public Usuario usuario;

}
