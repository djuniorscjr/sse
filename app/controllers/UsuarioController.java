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
import views.html.error;
import views.html.professor.continuaCadastroSRP;
import views.html.usuario.alterarSenha;
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

    private DynamicForm form = Form.form();

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
        DynamicForm formRequest = Form.form().bindFromRequest();
        Usuario usuario = new Usuario();
        usuario.id = u;
        usuario.email = formRequest.data().get("email");
        usuario.senha = formRequest.data().get("senha");
        String confirmaSenha = formRequest.data().get("confirmaSenha");

        try {
            Usuario atualizado = usuarioService.atualizar(usuario,confirmaSenha, session("email"));
            session().put("email", atualizado.email);
        }catch (RegraDeNegocioException e){
            flash("error",e.getMessage());
            return ok(editar.render(formRequest, session(),request(), flash()));
        }

        flash("sucesso", Messages.get("op.success"));
        return ok(editar.render(formRequest, session(), request(), flash()));
    }

    public Result esqueceuSenha(){
        return ok(esqueceuSenha.render(form, session(), request(), flash()));
    }

    public F.Promise<Result> enviandoEmailSenha(){
        DynamicForm formRequest = Form.form().bindFromRequest();
        try {
            usuarioService.esqueceuSenha(formRequest.get("email"));
        }catch (RegraDeNegocioException e){
            flash("error",e.getMessage());
            return F.Promise.promise(() -> ok(esqueceuSenha.render(formRequest, session(), request(), flash())));
        } catch (IOException e) {
            flash("error", e.getMessage());
            return F.Promise.promise(() -> ok(esqueceuSenha.render(formRequest, session(), request(), flash())));
        }
        flash("sucesso", Messages.get("op.success.send"));
        return F.Promise.promise(() -> redirect(routes.UsuarioController.esqueceuSenha()));
    }

    public Result alterarSenha(String token){
        Usuario usuario = usuarioService.retornaUsuarioPorToken(token);
        if(usuario == null){
            return ok(error.render("Página não encontrada."));
        }

        return ok(alterarSenha.render(form, usuario.email, session(), request(), flash()));
    }

    public Result alterandoSenha(){
        DynamicForm formRequest = Form.form().bindFromRequest();
        try {
            usuarioService.alterarSenha(formRequest.get("email"), formRequest.get("senha"), formRequest.get("senhaConfirmar"));
        }catch (RegraDeNegocioException e){
            flash("error",e.getMessage());
            return ok(alterarSenha.render(form, formRequest.get("email"), session(), request(), flash()));
        }
        flash("sucesso", Messages.get("op.success"));

        return ok(alterarSenha.render(form, formRequest.get("email"), session(), request(), flash()));
    }
}
