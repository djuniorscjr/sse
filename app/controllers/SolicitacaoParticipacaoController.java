package controllers;

import models.SolicitacaoDeParticipacao;
import models.StatusSolicitacaoDeParticipacao;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Result;
import service.ProjetoService;
import service.SolicitacaoParticipacaoService;
import util.RegraDeNegocioException;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Domingos Junior on 15/07/2015.
 */
public class SolicitacaoParticipacaoController extends BasicController {

    @Inject
    private SolicitacaoParticipacaoService solicitacaoParticipacaoService;
    @Inject
    private ProjetoService projetoService;

    public Result cadastrar(Long projetoId){
        DynamicForm form = Form.form().bindFromRequest();
        try{

            if(projetoService.projetoFechadaOuLotado(projetoId)){
                throw new RegraDeNegocioException("Projeto Fechado ou Lotado de participantes. Escolha outro projeto!");
            }
            solicitacaoParticipacaoService.salvar(projetoId, this.getUsuarioId(), form.get("observacao"));
            flash("sucesso", Messages.get("op.success"));
        }catch (RegraDeNegocioException e){
            flash("sucesso", e.getMessage());
        }

        flash("projeto", "OK");
        return redirect(routes.Application.admin());
    }

    public Result aceito(Long solicitacaoId, Long projetoId, Long alunoId){
        try{
            if(projetoService.projetoFechadaOuLotado(projetoId)){
                throw new RegraDeNegocioException("Projeto Fechado ou Lotado de participantes. Você não pode adicionar mais alunos!");
            }
            solicitacaoParticipacaoService.alterar(solicitacaoId, projetoId,alunoId ,"", StatusSolicitacaoDeParticipacao.ACEITO);
            flash("sucesso", Messages.get("op.success"));
        }catch (RegraDeNegocioException e){
            flash("sucesso", e.getMessage());
        }

        flash("projeto", "OK");
        return redirect(routes.Application.admin());
    }

    public Result negado(Long solicitacaoId, Long projetoId, Long alunoId){


        solicitacaoParticipacaoService.alterar(solicitacaoId,projetoId,alunoId,"",StatusSolicitacaoDeParticipacao.NEGADO);
        flash("sucesso", Messages.get("op.success"));

        flash("projeto", "OK");
        return redirect(routes.Application.admin());
    }

    public Result solicitacoesEnviadas(){
        solicitacaoParticipacaoService.retornaTodasAsSolicitacoesPorProfessor(this.getUsuarioId());
        return TODO;
    }

    public Result consultar(){
        List<SolicitacaoDeParticipacao> solicitacaoDeParticipacoes = solicitacaoParticipacaoService.retornaTodasAsSolicitacoesAceitasNegadasPorProfessor(this.getUsuarioId());
        return ok(views.html.solicitacao.consulta.render(solicitacaoDeParticipacoes,session(),request(),flash()));
    }
}
