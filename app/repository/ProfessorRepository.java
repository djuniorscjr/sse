package repository;

import com.avaje.ebean.Ebean;
import models.Professor;
import play.db.ebean.Transactional;

/**
 * Created by Domingos Junior on 01/06/2015.
 */
public class ProfessorRepository extends BasicRepository{

    public Professor retornaProfessorPorUsuarioId(Long id){
        return Ebean.find(Professor.class).fetch("usuario").where().eq("usuario.id", id).findUnique();
    }
}
