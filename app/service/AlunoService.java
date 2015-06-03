package service;

import com.google.inject.Inject;
import models.Aluno;
import models.Permissao;
import org.joda.time.DateTime;
import play.Play;
import play.libs.Crypto;
import repository.AlunoRepository;

/**
 * Created by Domingos Junior on 01/06/2015.
 */
public class AlunoService {

    @Inject
    private AlunoRepository alunoRepository;

    public void salvar(Aluno aluno){
        aluno.usuario.ativo = false;
        aluno.usuario.dataDeCadastro = DateTime.now();
        aluno.usuario.permissao = Permissao.ALUNO;
        Crypto crypto = Play.application().injector().instanceOf(Crypto.class);
        aluno.usuario.senha = crypto.encryptAES(aluno.usuario.senha);
        aluno.usuario.token = crypto.generateToken();
        alunoRepository.salvar(Aluno.class,aluno);
    }
}