package com.jsp.eventmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.eventmanagement.model.Event;

/**
 * This will give methods to perform CRUD operations
 */

public interface EventRepository extends JpaRepository<Event, Integer> {

}
