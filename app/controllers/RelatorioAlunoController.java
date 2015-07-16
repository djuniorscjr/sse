package controllers;

import models.Anexo;
import models.Arquivo;
import models.Usuario;
import org.joda.time.DateTime;
import play.i18n.Messages;
import play.mvc.Http;
import play.mvc.Result;
import service.AlunoService;
import service.ArquivoService;
import service.RelatorioService;
import util.RegraDeNegocioException;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Domingos Junior on 14/07/2015.
 */
public class RelatorioAlunoController extends BasicController {

    @Inject
    private ArquivoService arquivoService;

    public Result cadastrar(Long id) throws IOException {
        try {
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart file = body.getFile("arquivo");
            if (file == null) {
                throw new RegraDeNegocioException("Campo Arquivo é obrigátorio");
            }
            Arquivo arquivo = new Arquivo();
            arquivo.anexo = new Anexo();
            arquivo.anexo.arquivo = Files.readAllBytes(file.getFile().toPath());
            arquivo.anexo.contentType = file.getContentType();
            arquivo.anexo.descricao = file.getFilename();
            arquivoService.salvar(arquivo, this.getUsuarioId(),id);
            flash("sucesso", Messages.get("op.success"));
        }catch (RegraDeNegocioException e){
            flash("sucesso", e.getMessage());
        }
        flash("relatorio", "OK");
        return redirect(routes.Application.admin());
    }
}
