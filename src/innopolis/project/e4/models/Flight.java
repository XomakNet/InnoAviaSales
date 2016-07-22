package innopolis.project.e4.models;


import java.util.Date;

/**
 * Created by Xomak on 14.07.2016.
 * Stores flight-related information
 */
public class Flight implements Comparable {

    private int id;
    private int flightNumber;
    private Date departureDateTime;
    private Date arrivalDateTime;
    private Float cost;
    private int freePlaces;
    private String airline;
    private Airport from;
    private Airport to;

    public Flight(final int flightNumber, final Date departureDateTime, final Date arrivalDateTime, final Float cost, final int freePlaces, final String airline, final Airport from, final Airport to) {
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.cost = cost;
        this.freePlaces = freePlaces;
        this.airline = airline;
        this.from = from;
        this.to = to;
        this.flightNumber = flightNumber;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }

    public Date getArrivalDateTime() {
        return arrivalDateTime;
    }

    public Float getCost() {
        return cost;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(final int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public String getAirline() {
        return airline;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;

        Flight flight = (Flight) o;

        if (id != flight.id) return false;
        if (flightNumber != flight.flightNumber) return false;
        if (!departureDateTime.equals(flight.departureDateTime)) return false;
        return airline.equals(flight.airline);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + flightNumber;
        result = 31 * result + departureDateTime.hashCode();
        result = 31 * result + airline.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object obj) {
        Flight second = (Flight) obj;
        return this.departureDateTime.compareTo(second.departureDateTime);
    }
}
