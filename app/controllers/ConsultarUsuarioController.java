package controllers;

import action.Controle;
import models.Aluno;
import models.Permissao;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.AlunoService;
import views.html.aluno.consulta;

import javax.inject.Inject;

/**
 * Created by Domingos Junior on 19/06/2015.
 */
@Security.Authenticated(AutenticacaoSegura.class)
@Controle({Permissao.ADMINISTRADOR, Permissao.COORDENADOR,Permissao.PROFESSOR_DISCIPLINA})
public class ConsultarUsuarioController extends Controller {

    @Inject
    private AlunoService alunoService;

    public Result consulta(){
        return ok(consulta.render(alunoService.retornaTodos(),session(), request(), flash()));
    }

    public Result ativaAluno(Long id){
        Aluno aluno = alunoService.retornaPorId(id);
        aluno.usuario.ativo = true;
        alunoService.alterar(aluno);
        flash("sucesso", "Aluno ativado com sucesso!");
        return redirect(routes.ConsultarUsuarioController.consulta());
    }

    public Result bloquearAluno(Long id){
        Aluno aluno = alunoService.retornaPorId(id);
        aluno.usuario.ativo = false;
        alunoService.alterar(aluno);
        flash("sucesso", "Aluno bloqueado com sucesso!");
        return redirect(routes.ConsultarUsuarioController.consulta());
    }

}
