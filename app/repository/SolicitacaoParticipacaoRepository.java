package repository;

import com.avaje.ebean.Ebean;
import models.SolicitacaoDeParticipacao;
import models.StatusSolicitacaoDeParticipacao;

import java.util.List;

/**
 * Created by Domingos Junior on 15/07/2015.
 */
public class SolicitacaoParticipacaoRepository extends BasicRepository {
    public List<SolicitacaoDeParticipacao> recuperarTodasAsSolicitacoesPorProfessor(Long id) {
        return Ebean.find(SolicitacaoDeParticipacao.class).where()
                .eq("projeto.professor.usuario.id",id)
                .eq("statusSolicitacaoDeParticipacao", StatusSolicitacaoDeParticipacao.ENVIADO)
                .orderBy("projeto.titulo asc").findList();
    }

    public List<SolicitacaoDeParticipacao> recuperarTodasAsSolicitacoesAceitasNegadasPorProfessor(Long usuarioId) {
        return Ebean.find(SolicitacaoDeParticipacao.class).where()
                .eq("projeto.professor.usuario.id",usuarioId)
                .in("statusSolicitacaoDeParticipacao", StatusSolicitacaoDeParticipacao.ACEITO, StatusSolicitacaoDeParticipacao.NEGADO)
                .orderBy("projeto.titulo asc").findList();
    }
}
