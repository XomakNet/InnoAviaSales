package innopolis.project.e4.providers;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Xomak on 14.07.2016.
 * CSV-files based data-provider
 */
public class CSVFlightsDataProvider implements DataProvider {
    private HashMap<Airport, HashMap<Airport, List<Flight>>> flightsByAirports;

    public List<Airport> getAllAirports() {
        return null;
    }

    public List<Flight> getFlightsBetween(Airport from, Airport to) {
        return null;
    }

    @Override
    public User getUserById() {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean putUser(final User user) {
        return false;
    }

    @Override
    public boolean putFlight(final Flight flight) {
        return false;
    }
}
