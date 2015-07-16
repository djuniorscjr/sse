package service;

import models.Professor;
import models.Projeto;
import models.StatusProjeto;
import repository.ProjetoRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Domingos Junior on 25/06/2015.
 */
public class ProjetoService {

    @Inject
    private ProjetoRepository projetoRepository;
    @Inject
    private ProfessorService professorService;

    public Projeto salvar(Projeto projeto, Long usuarioId) {
        Professor professor = professorService.retornaProfessorPorUsuarioId(usuarioId);
        projeto.professor = professor;
        projeto.statusProjeto = StatusProjeto.ABERTO;
        return projetoRepository.salvar(projeto);
    }

    public Projeto retornarPorId(Long id) {
        return projetoRepository.getObjeto(Projeto.class, id);
    }

    public Projeto alterar(Projeto projeto) {
        return projetoRepository.editar(projeto);
    }

    public List<Projeto> retornaProjetoPorProfessor(Long usuarioId) {
        return projetoRepository.retornaListaPorCampo(Projeto.class, "professor.usuario.id", usuarioId);
    }

    public void remover(Long projeto) {
        projetoRepository.deletar(Projeto.class, projeto);
    }

    public List<Projeto> retornaTodosProjetoAbertos(){
        return projetoRepository.retornaListaPorCampo(Projeto.class,"statusProjeto",StatusProjeto.ABERTO);
    }

    public boolean projetoFechadaOuLotado(Long projetoId) {
        boolean bool = false;
        Projeto projeto = projetoRepository.getObjeto(Projeto.class, projetoId);
        if(projeto.alunos.size() >= projeto.quantidadeMaxDeParticipantes){
            bool = true;
        }else if(projeto.statusProjeto.equals(StatusProjeto.FECHADO)){
            bool = true;
        }
        return bool;
    }
}
