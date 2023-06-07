package com.meeting.calendar.entity;

import com.meeting.calendar.constants.CalendarAssistantConstants;
import com.meeting.calendar.constants.CalendarAssistantExceptionConstant;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    @NotNull(message = CalendarAssistantExceptionConstant.EMPTY_FIELD)
    private Long id;

    @Column(name="name")
    @NotNull(message = CalendarAssistantExceptionConstant.EMPTY_FIELD)
    private String name;

    @ManyToMany
    @Column(name = "meetings")
    @NotEmpty(message = CalendarAssistantExceptionConstant.EMPTY_FIELD)
    private List<Meeting> meetings;
}
