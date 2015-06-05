package controllers;

import com.avaje.ebean.Ebean;
import javax.inject.Inject;
import models.Aluno;
import models.Permissao;
import models.Usuario;
import org.joda.time.DateTime;
import play.api.mvc.Flash;
import play.data.Form;
import play.*;
import play.db.ebean.Transactional;
import play.libs.Crypto;
import play.mvc.*;

import repository.AlunoRepository;
import service.AlunoService;
import views.html.*;

/**
 * Created by Domingos Junior on 29/05/2015.
 */
public class AlunoController extends Controller {

    @Inject
    private AlunoService alunoService;

    private Form<Aluno> alunoForm = Form.form(Aluno.class);

    public Result novo(){
        return ok(views.html.aluno.novo.render(alunoForm, session(), flash()));
    }

    public Result cria(){
        Form<Aluno> aluno = Form.form(Aluno.class).bindFromRequest();
        if(aluno.hasErrors()){
            return badRequest(views.html.aluno.novo.render(aluno,session(), flash()));
        }else{
            alunoService.salvar(aluno.get());
            flash("sucesso", "Aluno cadastrado com sucesso!");
        }

        return redirect(routes.AlunoController.novo());
    }

}
