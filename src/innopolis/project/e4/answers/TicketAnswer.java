package innopolis.project.e4.answers;

/**
 * Created by Xomak on 15.07.2016.
 * Class, which describes answer on user's request for a ticket
 */
public class TicketAnswer {
    enum Status {SUCCESS, ERROR}
    private Status status;
    private String message;
    private int ticketID;

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getTicketID() {
        return ticketID;
    }

    public TicketAnswer(final Status status, final String message, final int ticketID) {

        this.status = status;
        this.message = message;
        this.ticketID = ticketID;
    }

}
