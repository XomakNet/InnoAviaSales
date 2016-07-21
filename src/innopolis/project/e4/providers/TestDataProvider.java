package innopolis.project.e4.providers;

import innopolis.project.e4.models.Airport;
import innopolis.project.e4.models.Flight;
import innopolis.project.e4.models.User;

import java.util.*;

/**
 * Created by Xomak on 19.07.2016.
 */
public class TestDataProvider implements DataProvider {
    private Set<Airport> airports = new HashSet<>();
    private HashMap<Airport, HashMap<Airport, Set<Flight>>> flightsByAirports = new HashMap<Airport, HashMap<Airport, Set<Flight>>>();

    public void addFlight(final Flight flight) {
        if(!airports.contains(flight.getFrom())) {
            airports.add(flight.getFrom());
        }
        if(!airports.contains(flight.getTo())) {
            airports.add(flight.getTo());
        }
        if (flightsByAirports.containsKey(flight.getFrom())) {
            HashMap<Airport, Set<Flight>> flightsToAirport = flightsByAirports.get(flight.getFrom());
            if (flightsToAirport.containsKey(flight.getTo())) {
                Set<Flight> flights = flightsToAirport.get(flight.getTo());
                flights.add(flight);
            }
            else {
                Set<Flight> flights = new HashSet<>();
                flights.add(flight);
                flightsToAirport.put(flight.getTo(), flights);
            }
        }
        else {
            Set<Flight> flights = new HashSet<>();
            flights.add(flight);
            HashMap<Airport, Set<Flight>> flightsToAirport = new HashMap<>();
            flightsToAirport.put(flight.getTo(), flights);
            flightsByAirports.put(flight.getFrom(), flightsToAirport);
        }
    }

    @Override
    public Set<Airport> getAllAirports() {
        return airports;
    }

    @Override
    public Set<Airport> getAirportsAchievableFrom(final Airport from) {
        if(flightsByAirports.containsKey(from)) {
            return flightsByAirports.get(from).keySet();
        }
        return null;
    }

    @Override
    public Set<Flight> getFlightsBetween(final Airport from, final Airport to) {
        if(flightsByAirports.containsKey(from)) {
            HashMap<Airport, Set<Flight>> flightsFromAirport = flightsByAirports.get(from);
            if(flightsFromAirport.containsKey(to)) {
                return flightsFromAirport.get(to);
            }
        }
        return null;
    }

    @Override
    public User getUserById(final int id) {
        return null;
    }

    @Override
    public Set<User> getAllUsers() {
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
