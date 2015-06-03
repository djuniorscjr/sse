package repository;

import com.avaje.ebean.Ebean;
import models.Usuario;

/**
 * Created by Domingos Junior on 03/06/2015.
 */
public class UsuarioRepository extends BasicRepository {

    public Usuario autenticacao(String email, String senha){
        return Ebean.find(Usuario.class).where().eq("email", email).eq("senha", senha).findUnique();
    }
}
