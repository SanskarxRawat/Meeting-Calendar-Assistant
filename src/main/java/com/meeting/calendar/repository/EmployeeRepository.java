package com.meeting.calendar.repository;


import com.meeting.calendar.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findAllByNameIn(List<String> participants);

    Employee findByName(String name);
}
