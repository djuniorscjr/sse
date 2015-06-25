package controllers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.projeto.novo;

/**
 * Created by Domingos Junior on 25/06/2015.
 */
public class ProjetoController extends Controller {

    public Result index(){
        DynamicForm dynamicForm = Form.form();
        return ok(novo.render(dynamicForm,session(),request(),flash()));
    }

    public Result cadastrar(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        return redirect(routes.ProjetoController.index());
    }
}
