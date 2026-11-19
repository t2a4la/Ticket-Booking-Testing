package edu.course.booking;

public class BookingManager {

    public void book(BookingRequest request) {

        if (request == null || !request.isValid()) {
            throw new IllegalArgumentException("Invalid booking request");
        }

    }
}