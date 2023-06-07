package com.meeting.calendar.exceptions;

public class NoMeetingConflictException extends RuntimeException {
    public NoMeetingConflictException(String message) {
        super(message);
    }
}
