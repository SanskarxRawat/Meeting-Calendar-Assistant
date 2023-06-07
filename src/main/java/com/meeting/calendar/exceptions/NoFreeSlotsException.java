package com.meeting.calendar.exceptions;

public class NoFreeSlotsException extends RuntimeException {
    public NoFreeSlotsException(String message) {
        super(message);
    }
}
