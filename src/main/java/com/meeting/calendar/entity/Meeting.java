package com.meeting.calendar.entity;

import com.meeting.calendar.converter.StringListConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "organizer")
    private String organizer;

    @Column(name = "agenda")
    private String agenda;

    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;

    @Column(name = "participants")
    @Convert(converter = StringListConverter.class)
    private List<String> participants;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
