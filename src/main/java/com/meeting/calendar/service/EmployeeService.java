package com.meeting.calendar.service;

import com.meeting.calendar.entity.Employee;
import com.meeting.calendar.entity.Meeting;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService {

    Boolean hasConflicts(List<Meeting> participantMeetings,LocalDateTime proposedStartTime, LocalDateTime proposedEndTime);

    Employee createEmployee(Employee employee);

    Employee getEmployeeByName(String name);
}
