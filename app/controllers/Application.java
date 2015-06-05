package controllers;

import javax.inject.Inject;
import models.Usuario;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
import play.libs.Crypto;
import play.mvc.*;
import service.UsuarioService;
import util.RegraDeNegocioException;
import views.html.*;
import views.html.usuario.login;

public class Application extends Controller {

    @Inject
    private UsuarioService usuarioService;
    private DynamicForm loginForm = Form.form();

    public Result index() {
        return ok(index.render("Your new application is ready.",session()));
    }

    public Result login(){
        return ok(login.render(loginForm,session(),flash()));
    }

    public Result autenticacao(){

        DynamicForm form = Form.form().bindFromRequest();
        String email = form.data().get("email");
        String senha = form.data().get("senha");

        Usuario usuario = null;
        try {
            usuario = usuarioService.autenticacao(email, senha);
        }catch (RegraDeNegocioException e){
            flash("error", e.getMessage());
            return redirect(routes.Application.login());
        }

        Crypto crypto = Play.application().injector().instanceOf(Crypto.class);
        session().put("email", usuario.email);
        session().put("permissao", usuario.permissao.name());
        session().put("p", crypto.encryptAES(usuario.permissao.name(), Play.application().configuration().getString("play.crypto.secret")));
        session().put("u", crypto.encryptAES(usuario.id.toString(), Play.application().configuration().getString("play.crypto.secret")));

        flash("sucesso", "Você conseguiu logar!");
        return redirect(routes.Application.login());
    }

    public Result logout(){
        session().remove("email");
        session().remove("permissao");
        session().clear();
        flash("sucesso", Messages.get("youve.been.logged.out"));
        return redirect(routes.Application.login());
    }
}
