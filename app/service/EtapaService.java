package service;

import models.Documento;
import repository.EtapaRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Domingos Junior on 21/06/2015.
 */
public class EtapaService {

    @Inject
    private EtapaRepository etapaRepository;

    public void salvar(Documento documento){
        etapaRepository.salvar(documento);
    }

    public Integer retornaPosicao(){
        return etapaRepository.retornaTodos(Documento.class).size() + 1;
    }

    public List<Documento> retornaTodosOrdenadoPorNumero(){
        return etapaRepository.retornaListaOrderbyCampo(Documento.class, "numero asc");
    }

    public Documento retornaPorId(Long id){
        return etapaRepository.getObjeto(Documento.class, id);
    }

    public Documento editar(Documento documento){
        return etapaRepository.editar(documento);
    }

    public void excluir(Long documento){
        etapaRepository.deletar(Documento.class, documento);
    }
}
