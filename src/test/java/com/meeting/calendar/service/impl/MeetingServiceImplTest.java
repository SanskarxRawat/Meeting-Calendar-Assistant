package com.meeting.calendar.service.impl;

import com.meeting.calendar.dto.request.MeetingRequest;
import com.meeting.calendar.entity.Employee;
import com.meeting.calendar.entity.Meeting;
import com.meeting.calendar.entity.TimeSlot;
import com.meeting.calendar.repository.EmployeeRepository;
import com.meeting.calendar.repository.MeetingRepository;
import com.meeting.calendar.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class MeetingServiceImplTest {

    @InjectMocks
    private MeetingServiceImpl meetingService;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeService employeeService;


    @Test
    void bookMeeting() {

        Employee employee=new Employee();
        employee.setName("abc");
        List<Meeting> meetingList=new ArrayList<>();
        Meeting meeting=new Meeting();
        meetingList.add(meeting);
        employee.setMeetings(meetingList);
        Mockito.when(employeeRepository.findByName("abc"))
                .thenReturn(employee);

        MeetingRequest meetingRequest=new MeetingRequest();
        meetingRequest.setStartTime(LocalDateTime.now());
        meetingRequest.setEndTime(LocalDateTime.now());
        meetingRequest.setParticipants(Arrays.asList("abc","abc"));

        meetingService.bookMeeting(meetingRequest);
    }

    @Test
    void findMeetingConflicts() {
        List<Employee> employeeList=new ArrayList<>();
        Employee employee=new Employee();
        List<Meeting> meetingList=new ArrayList<>();
        Meeting meeting=new Meeting();
        meeting.setStartTime(LocalDateTime.now());
        meeting.setEndTime(LocalDateTime.now());
        meetingList.add(meeting);
        employee.setMeetings(meetingList);
        employeeList.add(employee);
        Mockito.when(employeeRepository.findAllByNameIn(Arrays.asList("abc","abc")))
                .thenReturn(employeeList);

        MeetingRequest meetingRequest=new MeetingRequest();
        meetingRequest.setStartTime(LocalDateTime.now());
        meetingRequest.setEndTime(LocalDateTime.now());
        meetingRequest.setParticipants(Arrays.asList("abc","abc"));

        List<String> list=meetingService.findMeetingConflicts(meetingRequest);
        Assertions.assertNotNull(list);
    }

    @Test
    void findFreeSlots() {
        Employee employee=new Employee();
        List<Meeting> meetingList=new ArrayList<>();
        Meeting meeting=new Meeting();
        meeting.setStartTime(LocalDateTime.now());
        meeting.setEndTime(LocalDateTime.now());
        meetingList.add(meeting);
        employee.setMeetings(meetingList);

        Mockito.when(employeeRepository.findByName("abc"))
                .thenReturn(employee);
        List<TimeSlot> timeSlotList=meetingService.findFreeSlots("abc","abc",1);
        Assertions.assertNotNull(timeSlotList);
    }
}