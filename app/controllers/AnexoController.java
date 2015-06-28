package controllers;

import models.Anexo;
import models.Documento;
import play.mvc.Controller;
import play.mvc.Result;
import service.AnexoService;
import util.Arquivo;
import util.RegraDeNegocioException;

import javax.inject.Inject;

/**
 * Created by Domingos Junior on 26/06/2015.
 */
public class AnexoController extends Controller {

    @Inject
    private AnexoService anexoService;

    public Result dowloadArquivo(String id) throws RegraDeNegocioException {
        Anexo anexo = anexoService.retornaPorId(new Long(id));
        response().setContentType(anexo.contentType);
        response().setHeader("Content-disposition", "attachment; filename=" + anexo.descricao);
        return ok(Arquivo.returnaAnexo(anexo.descricao, anexo.arquivo));
    }
}
