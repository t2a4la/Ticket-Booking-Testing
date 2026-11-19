package edu.course.booking;

public interface INotificationService {

    void sendBookingConfirmation(String customerId, String eventId);

}