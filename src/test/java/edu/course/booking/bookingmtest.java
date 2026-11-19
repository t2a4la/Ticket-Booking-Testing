
package edu.course.booking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class BookingManagerTest {

    @Test
    void shouldRejectInvalidTicketQuantity() {
        BookingRequest request = new BookingRequest("C1", "E1", 6, 2500);

       assertFalse(request.isValid());
    }

    @Test
    void shouldRejectBookingWithSixTickets() {
        BookingRequest request = new BookingRequest("C1", "E1", 6, 2500);

       IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
INotificationService notificationService = mock(INotificationService.class);
IEventRepository eventRepository = mock(IEventRepository.class);

BookingManager manager = new BookingManager(
        paymentGateway,
        notificationService,
        eventRepository
);

        assertThrows(IllegalArgumentException.class,
                () -> manager.book(request));
    }
}
