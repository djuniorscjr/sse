package controllers;

import action.Controle;
import models.Permissao;
import models.Professor;
import models.Usuario;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.ProfessorService;
import service.UsuarioService;

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
            return notFound();
        }else if(usuario.ativo){
            flash("info", "Solicitação de Registro para Professor Orientador já utilizada.");
            return ok(views.html.professor.continuaCadastroSRP.render(srpForm,usuario,session(),flash()));
        }
        return ok(views.html.professor.continuaCadastroSRP.render(srpForm,usuario,session(),flash()));
    }

    public Result salvar(Long id){
        DynamicForm form = Form.form().bindFromRequest();
        Professor professor = professorService.retornaProfessorProUsuarioId(id);
        System.out.println(professor.nome);
        System.out.println(professor.usuario.email);
        String email = form.data().get("email");
        String senha = form.data().get("senha");
        System.out.println(email);
        System.out.println(senha);
        return TODO;
    }
}
