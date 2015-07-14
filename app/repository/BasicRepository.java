package repository;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Domingos Junior on 02/06/2015.
 */
public class BasicRepository {

    @Transactional
    public <T> T salvar(Object entity) {
        Ebean.save((T) entity);
        return (T) entity;
    }

    @Transactional
    public <T> T editar(Object entity){
        Ebean.update((T) entity);
        return (T) entity;
    }

    @Transactional
    public void deletar(Class<?> classToCast, Serializable id){
        Ebean.delete(classToCast, id);
    }

    public <T> T getObjeto(Class<T> classToCast, Serializable id){
        return Ebean.find(classToCast, id);
    }

    public <T> List<T> retornaTodos(Class<T> classToCast){
        return Ebean.find(classToCast).findList();
    }

    public <T> T retornaObjetoPorCampo(Class<T> classToCast, String campo, Object valor){
        return Ebean.find(classToCast).where().eq(campo, valor).setMaxRows(1).findUnique();
    }

    public <T> List<T> retornaListaPorCampo(Class<T> classToCast, String campo, Object valor){
        return Ebean.find(classToCast).where().eq(campo, valor).findList();
    }

    public <T> List<T> retornaListaPorData(Class<T> classToCast, String campo, Object dataInicio, Object dataFim){
        return Ebean.find(classToCast).where().between(campo, dataInicio, dataFim).findList();
    }

    public <T> List<T> retornaListaOrderbyCampo(Class<T> classToCast, String campo){
        return Ebean.find(classToCast).orderBy(campo).findList();
    }
}
