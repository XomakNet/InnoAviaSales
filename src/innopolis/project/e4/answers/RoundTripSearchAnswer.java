package innopolis.project.e4.answers;

import innopolis.project.e4.models.Path;

import java.util.List;

/**
 * Created by Xomak on 15.07.2016.
 * Stores answer on user's request for a round trip
 */
public class RoundTripSearchAnswer extends SearchAnswer {
    private List<Path> directPaths;
    private List<Path> returnPaths;

    public RoundTripSearchAnswer(final List<Path> directPaths, final List<Path> returnPaths) {
        this.directPaths = directPaths;
        this.returnPaths = returnPaths;
    }

    public List<Path> getDirectPaths() {
        return directPaths;
    }

    public List<Path> getReturnPaths() {
        return returnPaths;
    }
}
