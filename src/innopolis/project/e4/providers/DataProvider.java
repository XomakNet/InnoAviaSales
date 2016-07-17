package innopolis.project.e4.providers;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/***
 * CSV-files based data-provider
 */
public class DataProvider {
    private HashMap<Airport, HashMap<Airport, List<Flight>>> flightsByAirports;

    public Set<Airport> getAllAirports() {
        return null;
    }

    public Set<Flight> getFlightsBetween(Airport from, Airport to) {
        return null;
    }

    public User getUserById() {
        return null;
    }

    public Set<User> getAllUsers() {
        return null;
    }

    public boolean putUser(final User user) {
        return false;
    }

    public boolean putFlight(final Flight flight) {
        return false;
    }
}
