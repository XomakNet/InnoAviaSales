package innopolis.project.e4.providers;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.User;

import java.util.Set;

public interface DataProvider {

    Set<Airport> getAllAirports();

    Set<Airport> getAirportsAchievableFrom(final Airport from);

    Set<Flight> getFlightsBetween(Airport from, Airport to);

    User getUserById(final int id);

    Set<User> getAllUsers();

    boolean putUser(final User user);

    boolean putFlight(final Flight flight);
}
