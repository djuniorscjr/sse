package controllers;

import action.Controle;
import com.avaje.ebean.Ebean;
import javax.inject.Inject;
import models.Aluno;
import models.Permissao;
import models.Professor;
import org.joda.time.DateTime;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.ValidationError;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.libs.Crypto;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import service.ProfessorService;
import util.RegraDeNegocioException;
import views.html.professor.novo;
import views.html.professor.solicitarRegistro;

import java.io.IOException;

/**
 * Created by Domingos Junior on 29/05/2015.
 */
@Security.Authenticated(AutenticacaoSegura.class)
@Controle({Permissao.ADMINISTRADOR, Permissao.COORDENADOR,Permissao.PROFESSOR_DISCIPLINA})
public class ProfessorController extends Controller {

    private Form<Professor> professorForm = Form.form(Professor.class);
    private DynamicForm srpForm = Form.form();

    @Inject
    private ProfessorService professorService;


    public Result novo(){
        return ok(novo.render(professorForm,session(),request(),flash()));
    }

    public Result cria(){
        Form<Professor> professor = Form.form(Professor.class).bindFromRequest();
        if(professor.hasErrors()){
            return badRequest(novo.render(professor, session(), request(), flash()));
        }else{
            professorService.salvoPorOutroUser(professor.get());
            flash("sucesso", "Professor cadastrado com sucesso!");
        }

        return redirect(routes.ProfessorController.novo());
    }

    public Result solicitarRegistro(){
        flash("sucesso", Messages.get("infor.new.srp"));
        return ok(solicitarRegistro.render(srpForm, session(), request(), flash()));
    }

    public F.Promise<Result> enviarSolicitacaoRegistro(){
        DynamicForm form = Form.form().bindFromRequest();
        String emails = form.data().get("emails");

        try {
            professorService.enviarSolicitacaoRegistro(emails);
        }catch (RegraDeNegocioException e){
            flash("error", e.getMessage());
            return F.Promise.promise(() -> badRequest(solicitarRegistro.render(form, session(),request(), flash())));
        } catch (IOException e) {
            e.printStackTrace();
            flash("error", Messages.get("error.technical"));
        }
        flash("sucesso", "Operação realizada com sucesso!");
        return F.Promise.promise(() -> ok(solicitarRegistro.render(srpForm, session(), request(), flash())));
    }

}
