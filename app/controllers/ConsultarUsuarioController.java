package controllers;

import action.Controle;
import models.Aluno;
import models.Permissao;
import models.Professor;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.AlunoService;
import service.ProfessorService;
import util.RegraDeNegocioException;
import views.html.aluno.consulta;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Domingos Junior on 19/06/2015.
 */
@Security.Authenticated(AutenticacaoSegura.class)
@Controle({Permissao.ADMINISTRADOR, Permissao.COORDENADOR,Permissao.PROFESSOR_DISCIPLINA})
public class ConsultarUsuarioController extends Controller {

    @Inject
    private AlunoService alunoService;
    @Inject
    private ProfessorService professorService;

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

    public Result consultaProfessor(){
        return ok(views.html.professor.consulta.render(professorService.retornaTodos(), session(), request(), flash()));
    }

    public Result mudarStatus(Long id){
        Professor professor =  professorService.retornaProfessorPorId(id);
        Map<String,String> anyData = new HashMap<String, String>();
        anyData.put("permissao", professor.usuario.permissao.name());
        DynamicForm usuarioForm = Form.form().fill(anyData);
        return ok(views.html.usuario.mudaPermissao.render(usuarioForm, professor.id,session(),request(),flash()));
    }

    public Result mudarPermissao(Long id) throws RegraDeNegocioException {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        Permissao permissao = Permissao.valueOf(dynamicForm.get("permissao"));
        Professor professor =  professorService.retornaProfessorPorId(id);
        professor.usuario.permissao = permissao;
        professorService.alterar(professor);
        flash("sucesso", Messages.get("op.success"));
        return redirect(routes.ConsultarUsuarioController.consultaProfessor());
    }


}
