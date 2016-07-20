package innopolis.project.e4;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.providers.DataProvider;

import java.util.Date;
import java.util.List;

/**
 * Created by Xomak on 15.07.2016.
 * Implements some quick path search alghoritm
 */
public class SearchOptimizer {
    enum Criterion {COST, TIME}

    private DataProvider provider;

    public SearchOptimizer(final DataProvider provider) {
        this.provider = provider;
    }

    public void rebuildPathes() {

    }

    public List<Flight> getPathesBetween(final Airport src, final Airport dst, final Date date, final Criterion criterion) {
        return null;
    }
}
