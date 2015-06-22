package controllers;

import models.Anexo;
import models.Documento;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.EtapaService;
import views.html.etapa.consulta;
import views.html.etapa.novo;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Domingos Junior on 20/06/2015.
 */
public class EtapaController extends Controller{

    @Inject
    private EtapaService etapaService;

    private Form<Documento> form = Form.form(Documento.class);

    public Result index(){

        return ok(novo.render(form, session(), request(), flash()));
    }

    public Result cadastrar() throws IOException {
        Form<Documento> formRequest = Form.form(Documento.class).bindFromRequest();
        if(formRequest.hasErrors()){
            System.out.println(formRequest.errors().toString());
            return badRequest(novo.render(formRequest, session(), request(), flash()));
        }else{
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart arquivo = body.getFile("arquivo");
            Documento documento = formRequest.get();
            if(arquivo != null){
                File file = arquivo.getFile();
                documento.anexo = new Anexo();
                documento.anexo.arquivo = Files.readAllBytes(file.toPath());
                documento.anexo.contentType = arquivo.getContentType();
                documento.anexo.descricao = arquivo.getFilename();
            }
            documento.numero = etapaService.retornaPosicao();
            etapaService.salvar(documento);
            flash("sucesso", Messages.get("op.success"));
        }



        return redirect(routes.EtapaController.index());
    }
    public Result consulta(){
        List<Documento> documentos = etapaService.retornaTodosOrdenadoPorNumero();
        return ok(consulta.render(documentos, session(), request(), flash()));
    }

}
