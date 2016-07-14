package innopolis.project.e4.models;


import java.util.Date;

/**
 * Created by Xomak on 14.07.2016.
 * Stores flight-related information
 */
public class Flight {

    private int id;
    private Date departureDateTime;
    private Date arrivalDateTime;
    private Float cost;
    private int freePlaces;
    private String airline;

    public Flight(final Date departureDateTime, final Date arrivalDateTime, final Float cost, final int freePlaces, final String airline) {
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.cost = cost;
        this.freePlaces = freePlaces;
        this.airline = airline;
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
}
