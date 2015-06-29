import com.google.inject.Guice;
import com.google.inject.Injector;
import module.GlobalModule;
import play.Application;
import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import views.html.error;

/**
 * Created by Domingos Junior on 01/06/2015.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application application) {
        Injector injector = Guice.createInjector(new GlobalModule());
    }

    @Override
    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader arg){
        return F.Promise.<Result>pure(Results.notFound(error.render("Página não encontrada")));
    }

    @Override
    public F.Promise<Result> onError(Http.RequestHeader var1, Throwable var2){
        System.out.println(var2.getMessage());
        return F.Promise.<Result>pure(Results.internalServerError(error.render("Página não encontrada")));
    }

    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        return new Class[]{CSRFFilter.class};
    }
}
