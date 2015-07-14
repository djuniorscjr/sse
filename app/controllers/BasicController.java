package controllers;

import play.Play;
import play.libs.Crypto;
import play.mvc.Controller;

import javax.inject.Inject;

/**
 * Created by Domingos Junior on 27/06/2015.
 */
public class BasicController extends Controller {
    @Inject
    protected Crypto crypto;

    protected Long getUsuarioId(){
        return new Long(crypto.decryptAES(session().get("u"), Play.application().configuration().getString("play.crypto.secret")));
    }
}
