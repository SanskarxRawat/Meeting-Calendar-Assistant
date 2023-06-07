package com.meeting.calendar.service.impl;

import com.meeting.calendar.entity.Employee;
import com.meeting.calendar.entity.Meeting;
import com.meeting.calendar.repository.EmployeeRepository;
import com.meeting.calendar.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Boolean hasConflicts(List<Meeting> participantMeetings, LocalDateTime proposedStartTime, LocalDateTime proposedEndTime) {
        for (Meeting meeting : participantMeetings) {
            LocalDateTime existingStartTime = meeting.getStartTime();
            LocalDateTime existingEndTime = meeting.getEndTime();

            if (proposedStartTime.isBefore(existingEndTime) && proposedEndTime.isAfter(existingStartTime)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }
}
