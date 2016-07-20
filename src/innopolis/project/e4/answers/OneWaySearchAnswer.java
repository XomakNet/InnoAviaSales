package innopolis.project.e4.answers;

import innopolis.project.e4.models.Path;

import java.util.List;

/**
 * Created by Xomak on 15.07.2016.
 * Stores answer on user's request for a round trip
 */
public class OneWaySearchAnswer extends SearchAnswer {
    private List<Path> paths;

    public OneWaySearchAnswer(final List<Path> paths) {
        this.paths = paths;
    }

    public List<Path> getPaths() {
        return paths;
    }
}
