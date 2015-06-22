package service;

import akka.util.Crypt;
import javax.inject.Inject;

import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import models.Usuario;
import play.Play;
import play.i18n.Messages;
import play.libs.Crypto;
import repository.UsuarioRepository;
import util.Email;
import util.RegraDeNegocioException;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Domingos Junior on 03/06/2015.
 */
public class UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;
    @Inject
    private Crypto crypto;

    public Usuario autenticacao(String email, String senha) throws RegraDeNegocioException {
        this.validaLogin(email,senha);
        Usuario usuario = usuarioRepository.autenticacao(email, Crypt.sha1(senha));

        if(usuario == null){
            throw new RegraDeNegocioException(Messages.get("invalid.user.or.password"));
        }else if(!usuario.ativo){
            throw new RegraDeNegocioException(Messages.get("account.not.validated"));
        }
        return usuario;
    }

    private void validaLogin(String email, String senha) throws RegraDeNegocioException {
        if(email.isEmpty() || senha.isEmpty() || email.trim().isEmpty() || senha.trim().isEmpty()){
            throw new RegraDeNegocioException(Messages.get("error.all.required"));
        }
    }

    public Boolean jaExisteEmailCadastrado(String email){
        Usuario usuario = usuarioRepository.retornaObjetoPorCampo(Usuario.class, "email", email);
        return usuario == null? false : true;
    }

    public Usuario retornUsuarioPorEmail(String email){
        Usuario usuario = usuarioRepository.retornaObjetoPorCampo(Usuario.class, "email", email);
        return usuario;
    }

    public Usuario retornaUsuarioPorToken(String token){
        return usuarioRepository.retornaObjetoPorCampo(Usuario.class, "token", token);
    }

    public Usuario retornaUsuarioPorId(Long id){
        return usuarioRepository.getObjeto(Usuario.class, id);
    }

    public Usuario atualizar(Usuario usuario, String confirmaSenha, String email) throws RegraDeNegocioException {
        this.validaAtualizacao(usuario, confirmaSenha, email);
        usuario.senha = Crypt.sha1(usuario.senha);
        return usuarioRepository.editar(usuario);
    }

    private void validaAtualizacao(Usuario usuario, String confirmaSenha, String email) throws RegraDeNegocioException {
        Pattern regex = java.util.regex.Pattern.compile("\\b[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\b");
        if(usuario.email.isEmpty() || usuario.senha.isEmpty() || usuario.email.trim().isEmpty() || usuario.senha.trim().isEmpty()){
            throw new RegraDeNegocioException(Messages.get("error.all.required"));
        } if (!regex.matcher(usuario.email).matches()) {
            throw new RegraDeNegocioException(Messages.get("error.emails"));
        }else if(this.jaExisteEmailCadastrado(usuario.email) && !usuario.email.equals(email)){
            throw new RegraDeNegocioException(Messages.get("error.unique"));
        }else if(!usuario.senha.equals(confirmaSenha)){
            throw new RegraDeNegocioException(Messages.get("error.confirmation.password"));
        }
    }

    public void esqueceuSenha(String email) throws RegraDeNegocioException, IOException {
        this.validaEsqueceuSenha(email);
        MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
        recipient.setEmail(email);
        List<MandrillMessage.Recipient> recipients = new ArrayList<MandrillMessage.Recipient>();
        recipients.add(recipient);
        this.configurarEenviarEmail(recipients,this.retornUsuarioPorEmail(email).token);
    }

    private void validaEsqueceuSenha(String email) throws RegraDeNegocioException {
        Pattern regex = java.util.regex.Pattern.compile("\\b[a-zA-Z0-9.!#$%&\'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\\b");
        if(email.isEmpty() || email.trim().isEmpty()){
            throw new RegraDeNegocioException(Messages.get("error.email"));
        }else if (!regex.matcher(email).matches()) {
                    throw new RegraDeNegocioException(Messages.get("error.email"));
        }else if(!this.jaExisteEmailCadastrado(email)){
            throw new RegraDeNegocioException(Messages.get("error.email.not.record"));
        }
    }

    private void configurarEenviarEmail(List<MandrillMessage.Recipient> recipients, String token) throws IOException {
        URL resource = Play.application().classloader().getResource("senha");
        String url = resource.toString().replace("file:/", "");

        String readArquivo = new String(Files.readAllBytes(Paths.get(url)));
        readArquivo = readArquivo.replace("#LINK#", "http://localhost:9000/usuario/senha/" + token);
        Email email = new Email(readArquivo, "[SSE] Solicitação de Nova Senha", recipients);
        email.enviaEmail();
    }

    public void alterarSenha(String email, String senha, String confirmarSenha) throws RegraDeNegocioException {
        if(!senha.equals(confirmarSenha)){
            throw new RegraDeNegocioException(Messages.get("error.confirmation.password"));
        }
        Usuario usuario = this.retornUsuarioPorEmail(email);
        usuario.senha = Crypt.sha1(senha);
        usuario.token = crypto.generateToken();
        usuarioRepository.editar(usuario);
    }
}
