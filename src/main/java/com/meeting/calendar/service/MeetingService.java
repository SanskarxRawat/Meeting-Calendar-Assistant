package com.meeting.calendar.service;

import com.meeting.calendar.dto.request.MeetingRequest;
import com.meeting.calendar.entity.TimeSlot;

import java.util.List;

public interface MeetingService {

    void bookMeeting(MeetingRequest meetingRequest);

    List<String> findMeetingConflicts(MeetingRequest meetingRequest);

    List<TimeSlot> findFreeSlots(String name1,String name2,Integer durationInMinutes);
}
