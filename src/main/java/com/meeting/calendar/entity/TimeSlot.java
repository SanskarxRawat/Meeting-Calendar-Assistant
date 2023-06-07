package com.meeting.calendar.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TimeSlot {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
