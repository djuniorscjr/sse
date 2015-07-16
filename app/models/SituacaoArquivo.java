package models;

import org.joda.time.DateTime;
import play.data.format.Formats;

import javax.persistence.*;

/**
 * Created by Domingos Junior on 16/07/2015.
 */
@Entity
public class SituacaoArquivo {

    @Id
    @GeneratedValue
    public Long id;

    @Enumerated(EnumType.STRING)
    public StatusRelatorio statusRelatorio;

    @Formats.DateTime(pattern = "dd/MM/yyyy hh:mm:ss")
    public DateTime data;

    @ManyToOne
    public Arquivo projeto;

    @ManyToOne
    public Usuario usuario;

    @Column(columnDefinition = "TEXT")
    public String observacao;
}
