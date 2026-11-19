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

    public BookingResult book(BookingRequest request) {

        if (request == null || !request.isValid()) {
            throw new IllegalArgumentException("Invalid booking request");
        }
        if (eventRepository.isSoldOut(request.getEventId())) {
            throw new IllegalStateException("Event is sold out");
        }
        int amount = request.getSubtotal();

        String transactionId = paymentGateway.processPayment(amount);

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalStateException("Payment failed");
        }
        eventRepository.saveBooking(request);
        notificationService.sendBookingConfirmation(
                request.getCustomerId(),
                request.getEventId());
        return new BookingResult(transactionId);
    }
}