package controllers;

import com.avaje.ebean.Ebean;
import com.google.inject.Inject;
import models.Aluno;
import models.Permissao;
import models.Professor;
import org.joda.time.DateTime;
import play.Play;
import play.data.Form;
import play.db.ebean.Transactional;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import service.ProfessorService;

/**
 * Created by Domingos Junior on 29/05/2015.
 */
public class ProfessorController extends Controller {

    final static Form<Professor> professorForm = Form.form(Professor.class);

    @Inject
    private ProfessorService professorService;

    public Result novo(){
        return ok(views.html.professor.novo.render(professorForm,flash()));
    }

    public Result cria(){
        Form<Professor> professor = Form.form(Professor.class).bindFromRequest();
        if(professor.hasErrors()){
            return badRequest(views.html.professor.novo.render(professor,flash()));
        }else{
            professorService.salvar(professor.get());
            flash("sucesso", "Professor cadastrado com sucesso!");
        }

        return redirect(routes.ProfessorController.novo());
    }
}
