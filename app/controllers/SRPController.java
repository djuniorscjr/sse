package controllers;

import action.Controle;
import models.Permissao;
import models.Professor;
import models.Usuario;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.ProfessorService;
import service.UsuarioService;
import util.RegraDeNegocioException;
import views.html.error;
import views.html.professor.continuaCadastroSRP;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Domingos Junior on 05/06/2015.
 */

public class SRPController extends Controller {

    @Inject
    private ProfessorService professorService;
    @Inject
    private UsuarioService usuarioService;

    private DynamicForm srpForm = Form.form();

    public Result continuarSrp(String token){
        Usuario usuario = usuarioService.retornaUsuarioPorToken(token);
        if(usuario == null){
            return ok(error.render("Página não encontrada."));
        }else if(usuario.ativo){
            flash("info", "Solicitação de Registro para Professor Orientador já utilizada.");
            return ok(continuaCadastroSRP.render(srpForm, usuario, session(), request(), flash()));
        }
        return ok(continuaCadastroSRP.render(srpForm,usuario,session(),request(),flash()));
    }

    public Result salvar(Long id){
        DynamicForm form = Form.form().bindFromRequest();
        Professor professor = professorService.retornaProfessorPorUsuarioId(id);
        if(professor.usuario.ativo){
            flash("info", "Solicitação de Registro para Professor Orientador já utilizada.");
            return ok(continuaCadastroSRP.render(srpForm,professor.usuario,session(),request(),flash()));
        }
        professor.nome = form.data().get("nome");
        professor.usuario.senha = form.data().get("senha");
        try{
            professorService.alterarProfessor(professor);
        }catch (RegraDeNegocioException e){
            flash("error", e.getMessage());
            return ok(continuaCadastroSRP.render(form,professor.usuario,session(),request(),flash()));
        }
        flash("info", Messages.get("op.success"));
        return ok(continuaCadastroSRP.render(srpForm,professor.usuario,session(),request(),flash()));
    }
}
