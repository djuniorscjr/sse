package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.defaultpages.unauthorized;
import views.html.error;

/**
 * Created by Domingos Junior on 03/06/2015.
 */
public class AutenticacaoSegura extends Security.Authenticator {


    public String getUsername(Http.Context ctx) {
        return ctx.session().get("email");
    }

    public Result onUnauthorized(Http.Context ctx) {
        ctx.flash().put("error", "Para continuar precisa estar logado");
        return redirect(routes.Application.login());
    }

}
