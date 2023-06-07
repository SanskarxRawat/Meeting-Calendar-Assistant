package com.meeting.calendar.service.impl;

import com.meeting.calendar.entity.Employee;
import com.meeting.calendar.entity.Meeting;
import com.meeting.calendar.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;


    @Test
    void hasConflictsTest() {
        List<Meeting> meetingList=new ArrayList<>();
        Meeting meeting=new Meeting();
        meeting.setStartTime(LocalDateTime.now());
        meeting.setEndTime(LocalDateTime.now());
        meetingList.add(meeting);
        boolean ans= employeeService.hasConflicts(meetingList, LocalDateTime.now(),LocalDateTime.MAX);
        Assertions.assertFalse(ans);
    }

    @Test
    void createEmployee() {
        Employee employee=new Employee();
        employee.setName("abc");
        Mockito.when(employeeRepository.save(employee))
                .thenReturn(employee);
        Employee employee1=employeeService.createEmployee(employee);
        Assertions.assertEquals(employee1.getName(),employee.getName());

    }

    @Test
    void getEmployeeByName() {
        Employee employee=new Employee();
        employee.setName("abc");
        Mockito.when(employeeRepository.findByName("abc"))
                .thenReturn(employee);

        Employee employee1=employeeService.getEmployeeByName("abc");
        Assertions.assertEquals(employee1.getName(),employee.getName());
    }
}