package controllers;

import action.Controle;
import models.Anexo;
import models.Permissao;
import models.Projeto;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import service.ProjetoService;
import views.html.projeto.consulta;
import views.html.projeto.editar;
import views.html.projeto.novo;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Domingos Junior on 25/06/2015.
 */
@Security.Authenticated(AutenticacaoSegura.class)
@Controle({Permissao.PROFESSOR_DISCIPLINA})
public class ProjetoController extends BasicController {

    @Inject
    private ProjetoService projetoService;
    private Form<Projeto> projetoForm = Form.form(Projeto.class);

    public Result index(){
        return ok(novo.render(projetoForm, session(), request(), flash()));
    }

    public Result cadastrar() throws IOException {
        projetoForm = Form.form(Projeto.class).bindFromRequest();
        if(projetoForm.hasErrors()){
            return ok(novo.render(projetoForm, session(), request(), flash()));
        }else{
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart arquivo = body.getFile("arquivo");
            Projeto projeto = projetoForm.get();
            if(arquivo != null){
                File file = arquivo.getFile();
                projeto.anexo = new Anexo();
                projeto.anexo.arquivo = Files.readAllBytes(file.toPath());
                projeto.anexo.contentType = arquivo.getContentType();
                projeto.anexo.descricao = arquivo.getFilename();
            }
            projetoService.salvar(projeto);


            flash("sucesso", Messages.get("op.success"));
        }
        return redirect(routes.ProjetoController.index());
    }

    public Result editar(Long id){
        Projeto projeto = projetoService.retornarPorId(id);
        if(projeto.professor.id.equals(this.getUsuarioId())){
            return ok(views.html.error.render("Esse registro não existe!"));
        }
        projetoForm = Form.form(Projeto.class).fill(projeto);
        return ok(editar.render(projetoForm, id, session(), request(), flash()));
    }

    public Result editando(Long id) throws IOException {
        projetoForm = Form.form(Projeto.class).bindFromRequest();
        if(projetoForm.hasErrors()){
            return ok(editar.render(projetoForm, id, session(), request(), flash()));
        }else{
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart arquivo = body.getFile("arquivo");
            Projeto projeto = projetoForm.get();
            projeto.id = id;
            projeto.professor.id = this.getUsuarioId();
            if(arquivo != null){
                File file = arquivo.getFile();
                projeto.anexo = new Anexo();
                projeto.anexo.arquivo = Files.readAllBytes(file.toPath());
                projeto.anexo.contentType = arquivo.getContentType();
                projeto.anexo.descricao = arquivo.getFilename();
            }

            projetoService.alterar(projeto);
            flash("sucesso", Messages.get("op.success"));
        }

        return redirect(routes.ProjetoController.editar(id));
    }

    public Result consulta(){
        List<Projeto> projetos = projetoService.retornaProjetoPorProfessor(this.getUsuarioId());
        return ok(consulta.render(projetos,session(),request(),flash()));
    }

    public Result excluir(Long id){
        Projeto projeto = projetoService.retornarPorId(id);
        if(projeto.professor.id.equals(this.getUsuarioId())){
            return ok(views.html.error.render("Esse registro não existe!"));
        }

        projetoService.remover(id);
        flash("sucesso", Messages.get("op.success"));
        return redirect(routes.ProjetoController.consulta());
    }
}
