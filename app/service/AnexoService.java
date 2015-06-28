package service;

import models.Anexo;
import repository.AnexoRepository;

import javax.inject.Inject;

/**
 * Created by Domingos Junior on 26/06/2015.
 */
public class AnexoService {

    @Inject
    private AnexoRepository anexoRepository;

    public Anexo retornaPorId(Long id){
        return anexoRepository.getObjeto(Anexo.class, id);
    }
}
