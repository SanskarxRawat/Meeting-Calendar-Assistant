package com.meeting.calendar.service.impl;

import com.meeting.calendar.dto.request.MeetingRequest;
import com.meeting.calendar.entity.Employee;
import com.meeting.calendar.entity.Meeting;
import com.meeting.calendar.entity.TimeSlot;
import com.meeting.calendar.exceptions.SchedulingConflictException;
import com.meeting.calendar.repository.EmployeeRepository;
import com.meeting.calendar.repository.MeetingRepository;
import com.meeting.calendar.service.EmployeeService;
import com.meeting.calendar.service.MeetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void bookMeeting(MeetingRequest meetingRequest) {
        List<String> participants = meetingRequest.getParticipants();

        List<Employee> employees=new ArrayList<>();
        for(String participant:participants){
            Employee employee=employeeRepository.findByName(participant);
            employees.add(employee);
        }
        log.info("Employees : {}",employees);

        for (Employee employee : employees) {
            List<Meeting> participantMeetings = employee.getMeetings();
            if (employeeService.hasConflicts(participantMeetings, meetingRequest.getStartTime(), meetingRequest.getEndTime())) {
                throw new SchedulingConflictException("There is a scheduling conflict for participant: " + employee.getName());
            }
        }

        Meeting meeting = new Meeting();
        meeting.setAgenda(meetingRequest.getAgenda());
        meeting.setStartTime(meetingRequest.getStartTime());
        meeting.setEndTime(meetingRequest.getEndTime());

        for (Employee employee : employees) {
            List<Meeting> participantMeetings = employee.getMeetings();
            participantMeetings.add(meeting);
        }

        meetingRepository.save(meeting);
        log.info("Meeting Saved : {}",meeting);
        employeeRepository.saveAll(employees);
        log.info("Updated Employees : {}",employees);
    }

    @Override
    public List<String> findMeetingConflicts(MeetingRequest meetingRequest) {
        List<String> conflictingParticipants = new ArrayList<>();
        List<Employee> participantEmployees = employeeRepository.findAllByNameIn(meetingRequest.getParticipants());

        for (Employee employee : participantEmployees) {
            List<Meeting> employeeMeetings = employee.getMeetings();
            if (employeeService.hasConflicts(employeeMeetings, meetingRequest.getStartTime(), meetingRequest.getEndTime())) {
                conflictingParticipants.add(employee.getName());
            }
        }

        log.info("Conflicting participants : {}",conflictingParticipants);
        return conflictingParticipants;
    }


    @Override
    public List<TimeSlot> findFreeSlots(String name1, String name2, Integer durationInMinutes) {
        Employee employee1 = employeeRepository.findByName(name1);
        Employee employee2 = employeeRepository.findByName(name2);

        List<Meeting> employee1Meetings = employee1.getMeetings();
        List<Meeting> employee2Meetings = employee2.getMeetings();

        List<Meeting> allMeetings = new ArrayList<>();
        allMeetings.addAll(employee1Meetings);
        allMeetings.addAll(employee2Meetings);

        allMeetings.sort(Comparator.comparing(Meeting::getStartTime));

        log.info("Sorted Meeting List : {}",allMeetings);
        List<TimeSlot> freeSlots = new ArrayList<>();

        LocalDateTime startTime = LocalDateTime.now();

        for (Meeting meeting : allMeetings) {
            LocalDateTime meetingStartTime = meeting.getStartTime();
            LocalDateTime meetingEndTime = meeting.getEndTime();

            if (startTime.isBefore(meetingStartTime)) {
                LocalDateTime endTime = meetingStartTime.minusMinutes(1);

                long duration = Duration.between(startTime, endTime).toMinutes();

                if (duration >= durationInMinutes) {
                    TimeSlot freeSlot = new TimeSlot(startTime,endTime);
                    freeSlots.add(freeSlot);
                }
            }

            startTime = meetingEndTime.plusMinutes(1);
        }
        LocalDateTime endTime = LocalDateTime.MAX;
        long duration = Duration.between(startTime, endTime).toMinutes();
        if (duration >= durationInMinutes) {
            TimeSlot freeSlot = new TimeSlot(startTime, endTime);
            freeSlots.add(freeSlot);
        }

        log.info("List of Free Slots : {}",freeSlots);

        return freeSlots;

    }
}