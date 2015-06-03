package controllers;

import com.google.inject.Inject;
import models.Usuario;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;
import play.i18n.Messages;
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

        return ok(index.render("Your new application is ready."));
    }

    public Result login(){
        return ok(login.render(loginForm, flash()));
    }

    public Result autenticacao(){

        DynamicForm form = Form.form().bindFromRequest();
        String email = form.data().get("email");
        String senha = form.data().get("senha");
        try {
            usuarioService.autenticacao(email, senha);
        }catch (RegraDeNegocioException e){
            flash("error", e.getMessage());
            return redirect(routes.Application.login());
        }
        flash("sucesso", "Você conseguiu logar!");
        return ok(login.render(loginForm, flash()));
    }

}
