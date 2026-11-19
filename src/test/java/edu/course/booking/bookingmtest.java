
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
    
    @Test
    void shouldCompleteSuccessfulBooking() {

        BookingRequest request = new BookingRequest("C1", "E1", 2, 2500);

        IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
        INotificationService notificationService = mock(INotificationService.class);
        IEventRepository eventRepository = mock(IEventRepository.class);

        when(eventRepository.isSoldOut("E1")).thenReturn(false);
        when(paymentGateway.processPayment(5000)).thenReturn("TX123");

        BookingManager manager = new BookingManager(
                paymentGateway,
                notificationService,
                eventRepository);

        manager.book(request);

        verify(eventRepository).isSoldOut("E1");
        verify(paymentGateway).processPayment(5000);
        verify(eventRepository).saveBooking(request);
        verify(notificationService).sendBookingConfirmation("C1", "E1");
    }
    @Test
void shouldRejectBookingWhenEventIsSoldOut() {

    BookingRequest request =
            new BookingRequest("C1", "E1", 2, 2500);

    IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
    INotificationService notificationService = mock(INotificationService.class);
    IEventRepository eventRepository = mock(IEventRepository.class);

    when(eventRepository.isSoldOut("E1")).thenReturn(true);

    BookingManager manager = new BookingManager(
            paymentGateway,
            notificationService,
            eventRepository
    );

    assertThrows(IllegalStateException.class,
            () -> manager.book(request));

    verify(eventRepository, times(1)).isSoldOut("E1");

    verifyNoInteractions(
            paymentGateway,
            notificationService
    );

    verify(eventRepository, never()).saveBooking(any());
}

@Test
void shouldRejectBookingWhenPaymentFails() {

        BookingRequest request = new BookingRequest("C1", "E1", 2, 2500);

        IPaymentGateway paymentGateway = mock(IPaymentGateway.class);
        INotificationService notificationService = mock(INotificationService.class);
        IEventRepository eventRepository = mock(IEventRepository.class);

        when(eventRepository.isSoldOut("E1")).thenReturn(false);
        when(paymentGateway.processPayment(5000)).thenReturn(null);

        BookingManager manager = new BookingManager(
                        paymentGateway,
                        notificationService,
                        eventRepository);

        assertThrows(IllegalStateException.class,
                        () -> manager.book(request));

        verify(eventRepository, times(1)).isSoldOut("E1");
        verify(paymentGateway, times(1)).processPayment(5000);

        verify(eventRepository, never()).saveBooking(any());
        verifyNoInteractions(notificationService);
}
}
