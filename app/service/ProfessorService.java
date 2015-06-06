package service;

import akka.util.Crypt;
import javax.inject.Inject;

import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import models.Permissao;
import models.Professor;
import models.Usuario;
import org.joda.time.DateTime;
import play.Play;
import play.api.libs.concurrent.Promise;
import play.i18n.Messages;
import play.libs.Crypto;
import play.libs.F;
import play.mvc.Result;
import repository.ProfessorRepository;
import util.Email;
import util.RegraDeNegocioException;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Domingos Junior on 01/06/2015.
 */
public class ProfessorService {

    @Inject
    private ProfessorRepository professorRepository;
    @Inject
    private UsuarioService usuarioService;

    public Professor salvar(Professor professor){
        professor.usuario.ativo = false;
        professor.usuario.dataDeCadastro = DateTime.now();
        professor.usuario.permissao = Permissao.PROFESSOR_DISCIPLINA;
        Crypto crypto = Play.application().injector().instanceOf(Crypto.class);
        professor.usuario.senha = Crypt.sha1(professor.usuario.senha);
        professor.usuario.token = crypto.generateToken();
        return professorRepository.salvar(Professor.class, professor);
    }

    public void enviarSolicitacaoRegistro(String emails) throws RegraDeNegocioException, IOException {
        Set<String> listaDeEmails = this.validaEnviarSolicitacaoRegistro(emails);
        for (String email : listaDeEmails) {
            Professor professor = this.cadastrarProfessorProvisorio(email);
            MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
            recipient.setEmail(email);
            List<MandrillMessage.Recipient> recipients = new ArrayList<MandrillMessage.Recipient>();
            recipients.add(recipient);
            this.configurarEenviarEmail(recipients, professor.usuario.token);
        }
    }

    private void configurarEenviarEmail(List<MandrillMessage.Recipient> recipients, String token) throws IOException {
        URL resource = Play.application().classloader().getResource("dom");
        String url = resource.toString().replace("file:/", "");

        String readArquivo = new String(Files.readAllBytes(Paths.get(url)));
        readArquivo = readArquivo.replace("#LINK#", "http://localhost:9000/solicitarRegistro/" + token);
        Email email = new Email(readArquivo, "[SSE] Solicitação de Cadastro", recipients);
        email.enviaEmail();
    }

    private Set<String> validaEnviarSolicitacaoRegistro(String emails) throws RegraDeNegocioException {
        Pattern regex = java.util.regex.Pattern.compile("\\b[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\b");
        if(emails == null || emails.isEmpty() || emails.trim().isEmpty()){
            throw new RegraDeNegocioException(Messages.get("error.required"));
        }
        Set<String> listaDeEmails = new HashSet<String>();
        for(String email : emails.split(",")) {
            if (!regex.matcher(email).matches()) {
                throw new RegraDeNegocioException(Messages.get("error.emails"));
            }else if(usuarioService.jaExisteEmailCadastrado(email)){
                throw new RegraDeNegocioException(Messages.get("error.pendency") + " : " + email);
            }else{
                listaDeEmails.add(email);
            }
        }

        return listaDeEmails;
    }

    private Professor cadastrarProfessorProvisorio(String email){
        Professor professor = new Professor();
        professor.nome = "PROVISORIO";
        professor.usuario = new Usuario();
        professor.usuario.email = email;
        professor.usuario.senha = email;
        return this.salvar(professor);
    }

    public Professor retornaProfessorProUsuarioId(Long id){
        return professorRepository.retornaProfessorPorUsuarioId(id);
    }

    public void alterarProfessor(Professor professor) throws RegraDeNegocioException {
        this.validaAlteracaoProfessor(professor);
        professor.usuario.ativo = true;
        professor.usuario.permissao = Permissao.PROFESSOR_ORIENTADOR;
        professor.usuario.senha = Crypt.sha1(professor.usuario.senha);
        Crypto crypto = Play.application().injector().instanceOf(Crypto.class);
        professor.usuario.token = crypto.generateToken();
        professorRepository.editar(Professor.class, professor);
    }

    private void validaAlteracaoProfessor(Professor professor) throws RegraDeNegocioException {
        if(professor.nome == null || professor.usuario.senha == null || professor.nome.isEmpty() || professor.nome.trim().isEmpty() || professor.usuario.senha.isEmpty() || professor.usuario.senha.trim().isEmpty()){
            throw new RegraDeNegocioException(Messages.get("error.all.required"));
        }
    }
}
