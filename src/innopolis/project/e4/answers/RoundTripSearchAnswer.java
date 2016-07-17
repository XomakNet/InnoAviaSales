package innopolis.project.e4.answers;

import innopolis.project.e4.models.Flight;

import java.util.List;

/**
 * Created by Xomak on 15.07.2016.
 * Stores answer on user's request for a round trip
 */
public class RoundTripSearchAnswer extends SearchAnswer {
    private List<List<Flight>> directFlights;
    private List<List<Flight>> returnFlights;

    public RoundTripSearchAnswer(final List<List<Flight>> directFlights, final List<List<Flight>> returnFlights) {
        this.directFlights = directFlights;
        this.returnFlights = returnFlights;
    }

    public List<List<Flight>> getDirectFlights() {
        return directFlights;
    }

    public List<List<Flight>> getReturnFlights() {
        return returnFlights;
    }
}
