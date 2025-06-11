package com.jsp.eventmanagement.dao;

import java.time.LocalTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger logger = LoggerFactory.getLogger(EventDao.class) ;
	
	public Event saveEvent(Event event) {
		
		logger.debug("Saving event to DB : {}", event);
		
		return repository.save(event) ;
	}
	
	public Event findEventById(int id) {
		
		Optional<Event> foundEvent = repository.findById(id) ;
		
		if(foundEvent.isPresent()) {
			logger.debug("Found the event from DB : {}", foundEvent);
			return foundEvent.get() ;
		}
		
		else {
			logger.debug(LocalTime.now() +" Unable to find the Data from DB for : "+ id);
			return null ;
		}
	}
	
	public Event updateEvent(Event event) {
		
		logger.debug("Updated the Event in DB : {}", event);
		
		return repository.save(event) ;
		
	}
	
	public boolean deleteEvent(int id) {
		
		Event event = findEventById(id) ;
		
		if(event !=null) {
			logger.debug("Removed the Event from DB : {}", true);
			repository.delete(event);
			return true ;
		}
		else {
			logger.debug(LocalTime.now() +" Unable to Delete the Data from DB : "+ id);
			return false ;
		}
	}

}
