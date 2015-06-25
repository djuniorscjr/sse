package controllers;

import models.Projeto;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import service.ProjetoService;
import views.html.projeto.novo;

/**
 * Created by Domingos Junior on 25/06/2015.
 */
public class ProjetoController extends Controller {

    private ProjetoService projetoService;
    private Form<Projeto> projetoForm = Form.form(Projeto.class);

    public Result index(){
        return ok(novo.render(projetoForm,session(),request(),flash()));
    }

    public Result cadastrar(){
        projetoForm = Form.form(Projeto.class).bindFromRequest();
        if(projetoForm.hasErrors()){
            return ok(novo.render(projetoForm, session(), request(), flash()));
        }else{

        }

        return redirect(routes.ProjetoController.index());
    }
}
