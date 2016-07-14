package innopolis.project.e4.requests;

import innopolis.project.e4.models.Airport;

import java.util.Date;

/**
 * Created by Xomak on 15.07.2016.
 * Stores all request's parameter for searching process
 */
public class SearchRequest {
    private Airport from;
    private Airport to;
    private Date directDate;
    private Date returnDate;
    private boolean noTransfer;

    public Airport getFrom() {
        return from;
    }

    public void setFrom(final Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(final Airport to) {
        this.to = to;
    }

    public Date getDirectDate() {
        return directDate;
    }

    public void setDirectDate(final Date directDate) {
        this.directDate = directDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isNoTransfer() {
        return noTransfer;
    }

    public void setNoTransfer(final boolean noTransfer) {
        this.noTransfer = noTransfer;
    }

    public SearchRequest(final Airport from, final Airport to) {
        this.from = from;
        this.to = to;
    }
}
