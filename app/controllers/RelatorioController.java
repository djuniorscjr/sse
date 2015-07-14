package controllers;

import models.Anexo;
import models.Projeto;
import models.Relatorio;
import models.Usuario;
import org.joda.time.DateTime;
import play.data.DynamicForm;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.RelatorioService;
import util.Converter;
import util.RegraDeNegocioException;
import views.html.relatorio.consulta;
import views.html.relatorio.editar;
import views.html.relatorio.novo;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Domingos Junior on 30/06/2015.
 */
public class RelatorioController extends BasicController {

    @Inject
    private RelatorioService relatorioService;
    private DynamicForm relatorioForm = Form.form();

    public Result index(){
        Integer numeroDoRelatorio = relatorioService.recuperarNumeroDoRelatorio();
        return ok(novo.render(relatorioForm, numeroDoRelatorio, session(), request(), flash()));
    }

    public Result cadastrar() throws IOException {
        relatorioForm = Form.form().bindFromRequest();
        Integer numeroDoRelatorio = relatorioService.recuperarNumeroDoRelatorio();
        try {
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart arquivo = body.getFile("arquivo");

            Relatorio relatorio = new Relatorio();
            if(arquivo != null){
                File file = arquivo.getFile();
                relatorio.anexo = new Anexo();
                relatorio.anexo.arquivo = Files.readAllBytes(file.toPath());
                relatorio.anexo.contentType = arquivo.getContentType();
                relatorio.anexo.descricao = arquivo.getFilename();
            }
            relatorio.descricao = relatorioForm.get("descricao");

            relatorioService.salvar(relatorio, this.getUsuarioId(), relatorioForm.get("dataDeEntrega"));
        }catch (RegraDeNegocioException e){
            flash("error",e.getMessage());
            return ok(novo.render(relatorioForm,numeroDoRelatorio,session(), request(), flash()));
        }
        flash("sucesso", Messages.get("op.success"));
        return redirect(routes.RelatorioController.index());

    }

    public Result editar(Long id){
        Relatorio relatorio = relatorioService.recuperarPorId(id);
        Integer posicao = relatorioService.recuperarNumeroDaPosicao(relatorio.dataDeEntrega);

        Map<String,String> anyData = new HashMap<String, String>();
        anyData.put("dataDeEntrega", Converter.retornaStringPorData(relatorio.dataDeEntrega));
        anyData.put("descricao", relatorio.descricao);

        if(relatorio.anexo != null) {
            anyData.put("anexo.descricao", relatorio.anexo.descricao);
            anyData.put("anexo.id", relatorio.anexo.id.toString());
        }

        DynamicForm relatorioFormEdit = Form.form().fill(anyData);
        return ok(editar.render(relatorioFormEdit, posicao, id, session(), request(), flash()));
    }

    public Result editando(Long id) throws IOException {
        relatorioForm = Form.form().bindFromRequest();
        try {
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart arquivo = body.getFile("arquivo");

            Relatorio relatorio = new Relatorio();

            if(arquivo != null){
                File file = arquivo.getFile();
                relatorio.anexo = new Anexo();
                relatorio.anexo.arquivo = Files.readAllBytes(file.toPath());
                relatorio.anexo.contentType = arquivo.getContentType();
                relatorio.anexo.descricao = arquivo.getFilename();
            }
            relatorio.id = id;
            relatorio.descricao = relatorioForm.get("descricao");

            relatorioService.editar(relatorio, this.getUsuarioId(), relatorioForm.get("dataDeEntrega"));
        }catch (RegraDeNegocioException e){
            flash("error", e.getMessage());
            return ok(novo.render(relatorioForm,0,session(), request(), flash()));
        }
        flash("sucesso", Messages.get("op.success"));
        return redirect(routes.RelatorioController.editar(id));

    }

    public Result consultar(){
        List<Relatorio> relatorios = relatorioService.retornaTodos();
        return ok(consulta.render(relatorios, session(), request(), flash()));
    }

    public Result excluir(Long id){
        Relatorio relatorio = relatorioService.recuperarPorId(id);
        relatorioService.deletar(id);
        flash("sucesso", Messages.get("op.success"));
        return redirect(routes.RelatorioController.consultar());
    }
}
