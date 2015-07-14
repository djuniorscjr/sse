package models;

import org.joda.time.DateTime;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Domingos Junior on 24/06/2015.
 */
@Entity
public class Relatorio {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String descricao;

    @Formats.DateTime(pattern = "dd/MM/yyyy hh:mm:ss")
    public DateTime dataDeEntrega;

    @Enumerated(EnumType.STRING)
    public StatusRelatorio statusRelatorio;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public Anexo anexo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relatorio")
    public List<SituacaoRelatorio> situacoesRelatorio;

    @OneToMany(mappedBy = "relatorio")
    public List<Arquivo> arquivos;
}
