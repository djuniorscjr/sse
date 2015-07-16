package service;

import models.Arquivo;
import models.Relatorio;
import models.StatusRelatorio;
import org.joda.time.DateTime;
import repository.ArquivoRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Domingos Junior on 15/07/2015.
 */
public class ArquivoService {

    @Inject
    private ArquivoRepository arquivoRepository;
    @Inject
    private AlunoService alunoService;

    public void salvar(Arquivo arquivo, Long usuarioId, Long id){
        arquivo.relatorio = new Relatorio();
        arquivo.relatorio.id = id;

        arquivo.aluno = alunoService.retornarPorUsuario(usuarioId);

        arquivo.data = DateTime.now();
        arquivo.statusRelatorio = StatusRelatorio.EM_ANDAMENTO;
        arquivoRepository.salvar(arquivo);
    }

    public Arquivo recuperarPorRelatorio(Relatorio relatorio) {
        try {
            if(relatorio != null && relatorio.id != null) {
                return arquivoRepository.recuperarPorRelatorio(relatorio.id);
            }
        }catch (NullPointerException e){
            return null;
        }
        return null;
    }

    public List<Arquivo> retornaTodosArquivoOrientador(Long usuarioId) {
        return arquivoRepository.retornaListaPorCampo(Arquivo.class,"aluno.projeto.professor.usuario.id", usuarioId);
    }
}
