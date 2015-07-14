package controllers;

import play.i18n.Messages;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created by Domingos Junior on 14/07/2015.
 */
public class RelatorioAlunoController extends BasicController {

    public Result cadastrar(Long id){
        System.out.println("usuario  " + getUsuarioId());
        System.out.println("relatorio " + id);
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart arquivo = body.getFile("arquivo");
        flash("relatorio", "a");
        if(arquivo == null){
            flash("sucesso", "Campo Arquivo é obrigátorio");
            return redirect(routes.Application.admin());
        }
        flash("sucesso", Messages.get("op.success"));
        return TODO;
    }
}
