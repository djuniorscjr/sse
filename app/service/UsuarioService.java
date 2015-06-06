package service;

import akka.util.Crypt;
import javax.inject.Inject;
import models.Usuario;
import play.Play;
import play.i18n.Messages;
import play.libs.Crypto;
import repository.UsuarioRepository;
import util.RegraDeNegocioException;

import java.util.regex.Pattern;

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

    public Boolean jaExisteEmailCadastrado(String email){
        Usuario usuario = usuarioRepository.retornaObjetoPorCampo(Usuario.class, "email", email);
        return usuario == null? false : true;
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
        return usuarioRepository.editar(Usuario.class, usuario);
    }

    private void validaAtualizacao(Usuario usuario, String confirmaSenha, String email) throws RegraDeNegocioException {
        System.out.println(email);
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


}
