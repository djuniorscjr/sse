import com.google.inject.Guice;
import com.google.inject.Injector;
import module.GlobalModule;
import play.Application;
import play.GlobalSettings;

/**
 * Created by Domingos Junior on 01/06/2015.
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application application) {
        Injector injector = Guice.createInjector(new GlobalModule());
    }
}
