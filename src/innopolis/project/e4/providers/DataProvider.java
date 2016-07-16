package innopolis.project.e4.providers;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.User;

import java.util.List;

/**
 * Created by Xomak on 15.07.2016.
 * Abstract interface, which describes methods, needed to communicate between application and datasource
 */
public interface DataProvider {

    List<Airport> getAllAirports();

    List<Flight> getFlightsBetween(Airport from, Airport to);

    List<Airport> getAirportsConnectedWith(Airport from);

    User getUserById();

    List<User> getAllUsers();

    boolean putUser(User user);

    boolean putFlight(Flight flight);
}
