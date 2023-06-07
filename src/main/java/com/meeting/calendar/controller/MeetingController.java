package com.meeting.calendar.controller;


import com.meeting.calendar.dto.request.MeetingRequest;
import com.meeting.calendar.entity.TimeSlot;
import com.meeting.calendar.exceptions.SchedulingConflictException;
import com.meeting.calendar.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/meetings")
@Tag(name = "Meeting Controller",description = "Controller for managing meetings and related operations.")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @PostMapping("/book")
    @Operation(summary = "This api is used to book meeting.")
    public ResponseEntity<String> bookMeeting(@RequestBody @Valid MeetingRequest meetingRequest) {
        log.info("Meeting Request to Book: {}",meetingRequest);
        try {
            meetingService.bookMeeting(meetingRequest);
            return ResponseEntity.ok("Meeting booked successfully.");
        } catch (SchedulingConflictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/free-slots")
    @Operation(summary = "This meeting is used to find free slots for meeting between given two employees.")
    public ResponseEntity<List<TimeSlot>> findFreeSlots(
            @RequestParam("employee1") String employee1,
            @RequestParam("employee2") String employee2,
            @RequestParam("durationInMinutes") int durationInMinutes) {
        log.info("Finding Free Slots between employee1 : {} employee2 : {} for : {} duration (in minutes)",employee1,employee2,durationInMinutes);
        List<TimeSlot> freeSlots = meetingService.findFreeSlots(employee1, employee2, durationInMinutes);
        if(!freeSlots.isEmpty()){
            return ResponseEntity.ok(freeSlots);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PostMapping("/check-conflicts")
    @Operation(summary = "This api is used to find the list of employee having conflicts with the given meeting.")
    public ResponseEntity<List<String>> checkMeetingConflicts(@RequestBody @Valid MeetingRequest meetingRequest) {
        log.info("Finding Employees conflicting with meeting request : {}",meetingRequest);
        List<String> conflicts = meetingService.findMeetingConflicts(meetingRequest);
        if(!conflicts.isEmpty()){
            return ResponseEntity.ok(conflicts);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}