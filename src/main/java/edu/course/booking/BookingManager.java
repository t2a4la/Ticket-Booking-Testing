package edu.course.booking;

public class BookingManager {

    private final IPaymentGateway paymentGateway;
    private final INotificationService notificationService;
    private final IEventRepository eventRepository;

    public BookingManager(
            IPaymentGateway paymentGateway,
            INotificationService notificationService,
            IEventRepository eventRepository) {

        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
        this.eventRepository = eventRepository;
    }

    public void book(BookingRequest request) {

        if (request == null || !request.isValid()) {
            throw new IllegalArgumentException("Invalid booking request");
        }
    }
}