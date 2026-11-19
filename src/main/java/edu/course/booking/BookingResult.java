package edu.course.booking;

public class BookingResult {

    private final String transactionId;

    public BookingResult(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }
}