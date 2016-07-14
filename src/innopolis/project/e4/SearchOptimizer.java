package innopolis.project.e4;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.providers.DataProvider;

import java.util.List;

/**
 * Created by Xomak on 15.07.2016.
 * Implements some quick path search alghoritm
 */
public class SearchOptimizer {
    private DataProvider provider;

    public SearchOptimizer(final DataProvider provider) {
        this.provider = provider;
    }

    public void rebuildPathes() {

    }

    public List<Airport> getPathBetween(Airport src, Airport dst) {
        return null;
    }
}
