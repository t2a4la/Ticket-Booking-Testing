
package edu.course.booking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookingManagerTest {

    @Test
    void shouldRejectInvalidTicketQuantity() {
        BookingRequest request = new BookingRequest("C1", "E1", 6, 2500);

        assertEquals(6, request.getTicketQuantity());
    }

    @Test
    void shouldRejectBookingWithSixTickets() {
        BookingRequest request = new BookingRequest("C1", "E1", 6, 2500);

        BookingManager manager = new BookingManager();

        assertThrows(IllegalArgumentException.class,
                () -> manager.book(request));
    }
}
