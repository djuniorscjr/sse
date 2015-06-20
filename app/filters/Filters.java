package filters;

import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
import play.http.HttpFilters;

import javax.inject.Inject;

/**
 * Created by Domingos Junior on 19/06/2015.
 */
public class Filters implements HttpFilters {

    @Inject
    public CSRFFilter csrfFilter;

    @Override
    public EssentialFilter[] filters() {
        return new EssentialFilter[] { csrfFilter};
    }
}
