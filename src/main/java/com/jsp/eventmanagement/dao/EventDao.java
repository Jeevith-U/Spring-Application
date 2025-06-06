package com.jsp.eventmanagement.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.eventmanagement.Repository.EventRepository;
import com.jsp.eventmanagement.model.Event;

/**
 * To performe CRUD operations on the model / entity using EventRepository
 */

@Repository
public class EventDao {
	
	@Autowired
	private EventRepository repository ;
	
	public Event saveEvent(Event event) {
		
		return repository.save(event) ;
	}
	
	public Event findEventById(int id) {
		
		Optional<Event> foundEvent = repository.findById(id) ;
		
		if(foundEvent.isPresent()) return foundEvent.get() ;
		
		else return null ;
	}
	
	public Event updateEvent(Event event) {
		
		return repository.save(event) ;
	}
	
	public boolean deleteEvent(int id) {
		
		Event event = findEventById(id) ;
		
		if(event !=null) {
			repository.delete(event);
			return true ;
		}
		else return false ;
	}

}
