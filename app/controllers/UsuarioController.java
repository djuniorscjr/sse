package controllers;

import models.Usuario;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.UsuarioService;
import util.RegraDeNegocioException;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Domingos Junior on 05/06/2015.
 */
@Security.Authenticated(AutenticacaoSegura.class)
public class UsuarioController extends Controller {

    @Inject
    private UsuarioService usuarioService;
    private Crypto crypto = Play.application().injector().instanceOf(Crypto.class);
    private Long u = new Long(crypto.decryptAES(session().get("u"), Play.application().configuration().getString("play.crypto.secret")));

    public Result editar(){
        Usuario usuario = usuarioService.retornaUsuarioPorId(u);
        Map<String,String> anyData = new HashMap();
        anyData.put("email", usuario.email);
        anyData.put("password", "");
        DynamicForm usuarioForm = Form.form().fill(anyData);
        return ok(views.html.usuario.editar.render(usuarioForm,session(),flash()));
    }

    public Result edicao(){
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
            return ok(views.html.usuario.editar.render(form, session(), flash()));
        }

        flash("sucesso", Messages.get("op.success"));
        return ok(views.html.usuario.editar.render(form, session(), flash()));
    }
}
