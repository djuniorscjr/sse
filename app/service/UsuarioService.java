package service;

import akka.util.Crypt;
import com.google.inject.Inject;
import models.Usuario;
import play.Play;
import play.i18n.Messages;
import play.libs.Crypto;
import repository.UsuarioRepository;
import util.RegraDeNegocioException;

/**
 * Created by Domingos Junior on 03/06/2015.
 */
public class UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

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
}
