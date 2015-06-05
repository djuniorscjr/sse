package action;

import controllers.routes;
import models.Permissao;
import play.Play;
import play.libs.Crypto;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created by Domingos Junior on 04/06/2015.
 */
public class ControleAction extends Action<Controle> {
    private Crypto crypto = Play.application().injector().instanceOf(Crypto.class);
    private Boolean permitir = false;
    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        String permissao = crypto.decryptAES(context.session().get("p"), Play.application().configuration().getString("play.crypto.secret"));

        for(Permissao p : configuration.value()){
            if(p.name().equals(permissao)){
                permitir = true;
            }
        }
        if (!permitir) {
            return F.Promise.promise(() -> ok(views.html.error.render("Você não tem permissão a esta funcionalidade!")));
        }else{
            return delegate.call(context);
        }
    }
}
