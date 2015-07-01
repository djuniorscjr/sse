package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import service.RelatorioService;

import javax.inject.Inject;

/**
 * Created by Domingos Junior on 30/06/2015.
 */
public class RelatorioController extends BasicController {

    @Inject
    private RelatorioService relatorioService;

    public Result index(){
        return TODO;
    }

    public Result cadastrar(){
        return TODO;
    }

    public Result editar(Long id){
        return TODO;
    }

    public Result editando(Long id){
        return TODO;
    }
}
