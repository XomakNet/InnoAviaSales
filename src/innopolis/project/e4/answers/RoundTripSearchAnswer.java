package innopolis.project.e4.answers;

import innopolis.project.e4.models.Flight;

import java.util.List;

/**
 * Created by Xomak on 15.07.2016.
 * Stores answer on user's request for a round trip
 */
public class RoundTripSearchAnswer extends SearchAnswer {
    private List<Flight> directFlights;
    private List<Flight> returnFlights;
}
