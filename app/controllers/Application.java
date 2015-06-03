package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.*;
import play.mvc.*;

import service.AlunoService;
import views.html.*;

public class Application extends Controller {

    public Result index() {

        return ok(index.render("Your new application is ready."));
    }

}
