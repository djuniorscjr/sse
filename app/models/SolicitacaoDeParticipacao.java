package models;

import org.joda.time.DateTime;
import play.data.format.Formats;

import javax.persistence.*;

/**
 * Created by Domingos Junior on 14/07/2015.
 */
@Entity
public class SolicitacaoDeParticipacao {

    @Id
    @GeneratedValue
    public Long id;

    @Formats.DateTime(pattern = "dd/MM/yyyy hh:mm:ss")
    public DateTime data;

    @Column(columnDefinition = "TEXT")
    public String observacao;

    @Enumerated(EnumType.STRING)
    public StatusSolicitacaoDeParticipacao statusSolicitacaoDeParticipacao;

    @ManyToOne
    public Aluno aluno;

    @ManyToOne
    public Projeto projeto;
}
