package com.meeting.calendar.dto.request;

import com.meeting.calendar.constants.CalendarAssistantExceptionConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MeetingRequest {

    @NotBlank(message = CalendarAssistantExceptionConstant.EMPTY_FIELD)
    private String organizer;

    @NotBlank(message = CalendarAssistantExceptionConstant.EMPTY_FIELD)
    private String agenda;

    @NotNull(message = CalendarAssistantExceptionConstant.EMPTY_FIELD)
    private LocalDateTime startTime;

    @NotNull(message = CalendarAssistantExceptionConstant.EMPTY_FIELD)
    private LocalDateTime endTime;

    @NotEmpty(message = CalendarAssistantExceptionConstant.EMPTY_FIELD)
    private List<String> participants;

}
