package service;

import akka.util.Crypt;
import com.google.inject.Inject;
import models.Permissao;
import models.Professor;
import org.joda.time.DateTime;
import play.Play;
import play.libs.Crypto;
import repository.ProfessorRepository;

/**
 * Created by Domingos Junior on 01/06/2015.
 */
public class ProfessorService {

    @Inject
    private ProfessorRepository professorRepository;

    public void salvar(Professor professor){
        professor.usuario.ativo = false;
        professor.usuario.dataDeCadastro = DateTime.now();
        professor.usuario.permissao = Permissao.PROFESSOR_DISCIPLINA;
        Crypto crypto = Play.application().injector().instanceOf(Crypto.class);
        professor.usuario.senha = Crypt.sha1(professor.usuario.senha);
        professor.usuario.token = crypto.generateToken();
        professorRepository.salvar(Professor.class,professor);
    }
}
