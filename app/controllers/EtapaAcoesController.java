package controllers;

import action.Controle;
import models.Anexo;
import models.Documento;
import models.Permissao;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import service.EtapaService;
import util.Arquivo;
import util.RegraDeNegocioException;
import views.html.etapa.editar;
import views.html.etapa.novo;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Domingos Junior on 21/06/2015.
 */
@Security.Authenticated(AutenticacaoSegura.class)

public class EtapaAcoesController extends Controller {

    @Inject
    private EtapaService etapaService;

    @Controle({Permissao.ADMINISTRADOR, Permissao.COORDENADOR,Permissao.PROFESSOR_DISCIPLINA})
    public Result editar(Long id){
        Documento documento = etapaService.retornaPorId(id);
        Form<Documento> documentoForm = Form.form(Documento.class).fill(documento);
        return ok(editar.render(documentoForm, id, session(), request(), flash()));
    }

    @Controle({Permissao.ADMINISTRADOR, Permissao.COORDENADOR,Permissao.PROFESSOR_DISCIPLINA})
    public Result editando(Long id) throws IOException {
        Form<Documento> documentoForm = Form.form(Documento.class).bindFromRequest();
        if(documentoForm.hasErrors()){
            return badRequest(editar.render(documentoForm,id, session(), request(), flash()));
        }else{
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart arquivo = body.getFile("arquivo");
            Documento documento = etapaService.retornaPorId(id);
            Documento documentoRequest = documentoForm.get();
            documento.descricao = documentoRequest.descricao;
            documento.titulo = documentoRequest.titulo;
            if(arquivo != null){
                File file = arquivo.getFile();
                if(documento.anexo == null){
                    documento.anexo = new Anexo();
                }
                documento.anexo.arquivo = Files.readAllBytes(file.toPath());
                documento.anexo.contentType = arquivo.getContentType();
                documento.anexo.descricao = arquivo.getFilename();
            }
            documento = etapaService.editar(documento);
            documentoForm = Form.form(Documento.class).fill(documento);
            flash("sucesso", Messages.get("op.success"));
        }
        return ok(editar.render(documentoForm, id, session(), request(), flash()));
    }

    public Result editaOrdem(Long id){
        return TODO;
    }

    @Controle({Permissao.ADMINISTRADOR, Permissao.COORDENADOR,Permissao.PROFESSOR_DISCIPLINA})
    public Result excluir(Long id){
        etapaService.excluir(id);
        flash("sucesso", Messages.get("op.success"));
        return redirect(routes.EtapaController.consulta());
    }

    public Result dowloadArquivo(Long id) throws RegraDeNegocioException {
        Documento documento = etapaService.retornaPorId(id);
        response().setContentType(documento.anexo.contentType);
        response().setHeader("Content-disposition", "attachment; filename=" + documento.anexo.descricao);
        return ok(Arquivo.returnaAnexo(documento.anexo.descricao, documento.anexo.arquivo));
    }
}
