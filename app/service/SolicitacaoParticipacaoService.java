package service;

import models.Aluno;
import models.Projeto;
import models.SolicitacaoDeParticipacao;
import models.StatusSolicitacaoDeParticipacao;
import org.joda.time.DateTime;
import repository.SolicitacaoParticipacaoRepository;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Domingos Junior on 15/07/2015.
 */
public class SolicitacaoParticipacaoService {

    @Inject
    private SolicitacaoParticipacaoRepository solicitacaoParticipacaoRepository;
    @Inject
    private AlunoService alunoService;

    public void salvar(Long projetoId, Long usuarioId, String observacao) {

        SolicitacaoDeParticipacao solicitacaoDeParticipacao = new SolicitacaoDeParticipacao();
        solicitacaoDeParticipacao.aluno = alunoService.retornarPorUsuario(usuarioId);
        solicitacaoDeParticipacao.data = DateTime.now();
        solicitacaoDeParticipacao.projeto = new Projeto();
        solicitacaoDeParticipacao.projeto.id = projetoId;
        solicitacaoDeParticipacao.statusSolicitacaoDeParticipacao = StatusSolicitacaoDeParticipacao.ENVIADO;
        solicitacaoDeParticipacao.observacao = observacao;
        solicitacaoParticipacaoRepository.salvar(solicitacaoDeParticipacao);
    }

    public List<SolicitacaoDeParticipacao> retornaTodasAsSolicitacoesPorProfessor(Long id){
        return solicitacaoParticipacaoRepository.recuperarTodasAsSolicitacoesPorProfessor(id);
    }


    public List<SolicitacaoDeParticipacao> retornaTodasAsSolicitacoesAceitasNegadasPorProfessor(Long usuarioId) {
        return solicitacaoParticipacaoRepository.recuperarTodasAsSolicitacoesAceitasNegadasPorProfessor(usuarioId);
    }

    public void alterar(Long solicitacaoId, Long projetoId, Long alunoid, String observacao, StatusSolicitacaoDeParticipacao status) {
        SolicitacaoDeParticipacao solicitacaoDeParticipacao = new SolicitacaoDeParticipacao();

        solicitacaoDeParticipacao.id = solicitacaoId;
        solicitacaoDeParticipacao.aluno = new Aluno();
        solicitacaoDeParticipacao.aluno.id = alunoid;
        solicitacaoDeParticipacao.data = DateTime.now();
        solicitacaoDeParticipacao.projeto = new Projeto();
        solicitacaoDeParticipacao.projeto.id = projetoId;
        solicitacaoDeParticipacao.statusSolicitacaoDeParticipacao = status;
        if(status == StatusSolicitacaoDeParticipacao.ACEITO){
            this.adicionarProjetoAoAluno(solicitacaoDeParticipacao.aluno, projetoId);
        }
        solicitacaoDeParticipacao.observacao = observacao;
        solicitacaoParticipacaoRepository.editar(solicitacaoDeParticipacao);
    }

    private void adicionarProjetoAoAluno(Aluno aluno, Long projetoId) {
        aluno.projeto = new Projeto();
        aluno.projeto.id = projetoId;
        alunoService.alterar(aluno);
    }

    public SolicitacaoDeParticipacao retornarPorId(Long id){
        return solicitacaoParticipacaoRepository.getObjeto(SolicitacaoDeParticipacao.class, id);
    }
}
