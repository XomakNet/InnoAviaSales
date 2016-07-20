package innopolis.project.e4.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the path from one airport to another.
 */
public class Path {
    private List<Flight> flightsSequence;

    public Path(final List<Flight> flightsSequence) {
        this.flightsSequence = flightsSequence;
    }

    public Path() {
        this.flightsSequence = new LinkedList<>();
    }

    public List<Flight> getFlightsSequence() {
        return flightsSequence;
    }

    public void addFlight(final Flight flight) {
        this.flightsSequence.add(flight);
    }
}
