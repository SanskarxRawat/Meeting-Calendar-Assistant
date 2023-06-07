package com.meeting.calendar.repository;


import com.meeting.calendar.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {

}
