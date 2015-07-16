package repository;

import com.avaje.ebean.Ebean;
import models.Arquivo;

/**
 * Created by Domingos Junior on 15/07/2015.
 */
public class ArquivoRepository extends BasicRepository {
    public Arquivo recuperarPorRelatorio(Long id) {
        return Ebean.find(Arquivo.class).where().eq("relatorio.id", id).setMaxRows(1).findUnique();
    }
}
