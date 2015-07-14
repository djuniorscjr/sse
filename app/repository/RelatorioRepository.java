package repository;

import com.avaje.ebean.Ebean;
import models.Relatorio;
import models.StatusRelatorio;
import org.joda.time.DateTime;

import java.util.Set;

/**
 * Created by Domingos Junior on 25/06/2015.
 */
public class RelatorioRepository extends BasicRepository {

    public Set<Relatorio> retornarPosicaoDoRelatorio(DateTime data) {
        return Ebean.find(Relatorio.class).orderBy("dataDeEntrega asc").findSet();

    }

    public Relatorio retornarProximoRelatorio() {
        return Ebean.find(Relatorio.class).where().in("statusRelatorio", StatusRelatorio.INICIADO, StatusRelatorio.NAO_INICIADO)
                .orderBy("dataDeEntrega asc").setMaxRows(1).findUnique();

    }
}
