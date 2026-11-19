
package edu.course.booking;

public class BookingRequest {

    private final String customerId;
    private final String eventId;
    private final int ticketQuantity;
    private final int pricePerTicketInCents;

    public BookingRequest(String customerId, String eventId,
            int ticketQuantity, int pricePerTicketInCents) {
        this.customerId = customerId;
        this.eventId = eventId;
        this.ticketQuantity = ticketQuantity;
        this.pricePerTicketInCents = pricePerTicketInCents;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEventId() {
        return eventId;
    }

    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public int getPricePerTicketInCents() {
        return pricePerTicketInCents;
    }

    public boolean isValid() {
        return customerId != null && !customerId.isBlank()
                && eventId != null && !eventId.isBlank()
                && ticketQuantity >= 1 && ticketQuantity <= 5
                && pricePerTicketInCents > 0;
    }
    public int getSubtotal() {
    return ticketQuantity * pricePerTicketInCents;
}
}
