package service;

import models.Relatorio;
import models.SituacaoRelatorio;
import models.StatusRelatorio;
import models.Usuario;
import org.joda.time.DateTime;
import play.i18n.Messages;
import repository.BasicRepository;
import repository.RelatorioRepository;
import util.Converter;
import util.RegraDeNegocioException;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

/**
 * Created by Domingos Junior on 25/06/2015.
 */
public class RelatorioService {
    @Inject
    private RelatorioRepository relatorioRepository;
    @Inject
    private BasicRepository basicRepository;

    public Integer recuperarNumeroDoRelatorio(){
        List<Relatorio> relatorios = relatorioRepository.retornaTodos(Relatorio.class);
        int tamanho = relatorios.size();
        return tamanho + 1;
    }

    public void salvar(Relatorio relatorio, Long usuarioId, String dataDeEntrega) throws RegraDeNegocioException {
        this.validarCadastro(relatorio, dataDeEntrega);
        relatorio.statusRelatorio = StatusRelatorio.NAO_INICIADO;
        relatorio.dataDeEntrega = Converter.retornaDataPorString(dataDeEntrega);
        Relatorio relatorioSalvo = relatorioRepository.salvar(relatorio);
        SituacaoRelatorio situacaoRelatorio = new SituacaoRelatorio();
        situacaoRelatorio.data = DateTime.now();
        situacaoRelatorio.relatorio = relatorio;
        situacaoRelatorio.statusRelatorio = StatusRelatorio.NAO_INICIADO;
        situacaoRelatorio.usuario = new Usuario();
        situacaoRelatorio.usuario.id = usuarioId;
        basicRepository.salvar(situacaoRelatorio);
    }

    private void validarCadastro(Relatorio relatorio, String dataDeEntrega) throws RegraDeNegocioException {
        if(relatorio.descricao.isEmpty() || relatorio.descricao.trim().isEmpty()){
            throw new RegraDeNegocioException("O campo descrição é obrigátorio");
        }
        if( dataDeEntrega.isEmpty() || dataDeEntrega.trim().isEmpty()){
            throw new RegraDeNegocioException("O campo data de entrega é obrigátorio");
        }
        if(estaDataJaTemRelatorio(dataDeEntrega, relatorio.id)){
            throw new RegraDeNegocioException("Existe um relátorio cadastrado nessa data");
        }
    }

    public List<Relatorio> retornaTodos() {
        return relatorioRepository.retornaTodos(Relatorio.class);
    }

    private boolean estaDataJaTemRelatorio(String data, Long id){
        Relatorio relatorio = relatorioRepository.retornaObjetoPorCampo(Relatorio.class, "dataDeEntrega", Converter.retornaDataPorString(data));
        boolean bool = false;
        if(relatorio == null) {
            bool = Boolean.FALSE;
        }else{
            if(id == null){
                bool = Boolean.FALSE;
            }else if (relatorio.id != id){
                bool = Boolean.TRUE;
            }
        }
        return bool;
    }

    public Relatorio recuperarPorId(Long id) {
        return relatorioRepository.getObjeto(Relatorio.class, id);
    }

    public Integer recuperarNumeroDaPosicao(DateTime data){
        Set<Relatorio> relatorios = relatorioRepository.retornarPosicaoDoRelatorio(data);
        Integer contador = 0;
        Integer posicao = 0;
        for(Relatorio rel : relatorios){
            contador++;
            if(rel.dataDeEntrega == data){
                posicao = contador;
            }
        }
        return contador;
    }

    public Relatorio editar(Relatorio relatorio, Long usuarioId, String dataDeEntrega) throws RegraDeNegocioException {
        this.validarCadastro(relatorio, dataDeEntrega);
        relatorio.dataDeEntrega = Converter.retornaDataPorString(dataDeEntrega);
        relatorioRepository.editar(relatorio);
        return relatorioRepository.editar(relatorio);
    }

    public void deletar(Long id) {
        relatorioRepository.deletar(Relatorio.class, id);
    }

    public Relatorio proximoRelatorio(){
        Relatorio relatorio = relatorioRepository.retornarProximoRelatorio();
        while(DateTime.now().isBefore(relatorio.dataDeEntrega)){
            this.mudancaRelatorio(relatorio,StatusRelatorio.FECHADO);
            relatorio = relatorioRepository.retornarProximoRelatorio();
        }

        if(relatorio.statusRelatorio == StatusRelatorio.NAO_INICIADO){
            this.mudancaRelatorio(relatorio, StatusRelatorio.INICIADO);
        }
        return relatorio;
    }

    private void mudancaRelatorio(Relatorio relatorio, StatusRelatorio statusRelatorio){
        relatorio.statusRelatorio = statusRelatorio;
        SituacaoRelatorio situacaoRelatorio = new SituacaoRelatorio();
        situacaoRelatorio.data = DateTime.now();
        situacaoRelatorio.relatorio = relatorio;
        situacaoRelatorio.statusRelatorio = statusRelatorio;
        relatorioRepository.editar(relatorio);
        basicRepository.salvar(situacaoRelatorio);
    }
}
