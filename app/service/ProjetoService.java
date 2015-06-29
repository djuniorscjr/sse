package service;

import models.Projeto;
import repository.ProjetoRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Domingos Junior on 25/06/2015.
 */
public class ProjetoService {

    @Inject
    private ProjetoRepository projetoRepository;

    public Projeto salvar(Object projeto) {
        return projetoRepository.salvar(projeto);
    }

    public Projeto retornarPorId(Long id) {
        return projetoRepository.getObjeto(Projeto.class, id);
    }

    public Projeto alterar(Projeto projeto) {
        return projetoRepository.editar(projeto);
    }

    public List<Projeto> retornaProjetoPorProfessor(Long usuarioId) {
        return projetoRepository.retornaListaPorCampo(Projeto.class, "professor.id", usuarioId);
    }

    public void remover(Long projeto) {
        projetoRepository.deletar(Projeto.class, projeto);
    }
}
