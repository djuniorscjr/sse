package controllers;

import action.Controle;
import models.Permissao;
import models.Usuario;
import play.Play;
import play.api.libs.concurrent.Promise;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Crypto;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.UsuarioService;
import util.RegraDeNegocioException;
import views.html.aluno.consulta;
import views.html.usuario.editar;
import views.html.usuario.esqueceuSenha;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Domingos Junior on 05/06/2015.
 */

public class UsuarioController extends Controller {

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private Crypto crypto;

    @Security.Authenticated(AutenticacaoSegura.class)
    public Result editar(){
        Long u = new Long(crypto.decryptAES(session().get("u"), Play.application().configuration().getString("play.crypto.secret")));

        Usuario usuario = usuarioService.retornaUsuarioPorId(u);
        Map<String,String> anyData = new HashMap<String, String>();
        anyData.put("email", usuario.email);
        anyData.put("password", "");
        DynamicForm usuarioForm = Form.form().fill(anyData);
        return ok(editar.render(usuarioForm,session(),request(),flash()));
    }

    @Security.Authenticated(AutenticacaoSegura.class)
    public Result edicao(){
        Long u = new Long(crypto.decryptAES(session().get("u"), Play.application().configuration().getString("play.crypto.secret")));
        DynamicForm form = Form.form().bindFromRequest();
        Usuario usuario = new Usuario();
        usuario.id = u;
        usuario.email = form.data().get("email");
        usuario.senha = form.data().get("senha");
        String confirmaSenha = form.data().get("confirmaSenha");

        try {
            Usuario atualizado = usuarioService.atualizar(usuario,confirmaSenha, session("email"));
            session().put("email", atualizado.email);
        }catch (RegraDeNegocioException e){
            flash("error",e.getMessage());
            return ok(editar.render(form, session(),request(), flash()));
        }

        flash("sucesso", Messages.get("op.success"));
        return ok(editar.render(form, session(), request(), flash()));
    }

    public Result esqueceuSenha(){
        DynamicForm form = Form.form();
        return ok(esqueceuSenha.render(form, session(), request(), flash()));
    }

    public F.Promise<Result> alterarSenha(){
        DynamicForm form = Form.form().bindFromRequest();
        try {
            usuarioService.esqueceuSenha(form.get("email"));
        }catch (RegraDeNegocioException e){
            flash("error",e.getMessage());
            return F.Promise.promise(() -> ok(esqueceuSenha.render(form, session(), request(), flash())));
        } catch (IOException e) {
            flash("error", e.getMessage());
            return F.Promise.promise(() -> ok(esqueceuSenha.render(form, session(), request(), flash())));
        }
        flash("sucesso", Messages.get("op.success.send"));
        return F.Promise.promise(() -> redirect(routes.UsuarioController.esqueceuSenha()));
    }

    @Security.Authenticated(AutenticacaoSegura.class)
    @Controle({Permissao.ADMINISTRADOR, Permissao.COORDENADOR,Permissao.PROFESSOR_DISCIPLINA})
    public Result consulta(){
        return ok(consulta.render(session(), request(), flash()));
    }
}
