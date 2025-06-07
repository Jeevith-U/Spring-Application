package com.jsp.eventmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.eventmanagement.Exception.EventNotFoundException;
import com.jsp.eventmanagement.Exception.UnableToSaveEventException;
import com.jsp.eventmanagement.Response.ResponseStructure;
import com.jsp.eventmanagement.dao.EventDao;
import com.jsp.eventmanagement.model.Event;

@Service
public class EventService {

	@Autowired
	private EventDao dao;

	public ResponseEntity<ResponseStructure<Event>> saveEvent(Event event) {

		if (event.getId() != 0 && event.getName() != null && event.getDiscription() != null) {

			Event savedEvent = dao.saveEvent(event);

			ResponseStructure<Event> response = new ResponseStructure<Event>();
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Event saved to the databse sucessfully");
			response.setData(savedEvent);

			return new ResponseEntity<ResponseStructure<Event>>(response, HttpStatus.CREATED);
		} else
			throw new UnableToSaveEventException("Missing attributes...");
	}

	public ResponseEntity<ResponseStructure<Event>> findEventById(int id) {

		Event foundEvent = dao.findEventById(id);

		if (foundEvent != null) {

			ResponseStructure<Event> response = new ResponseStructure<Event>();
			response.setStatusCode(HttpStatus.FOUND.value());
			response.setMessage("Event Found in the databse sucessfully");
			response.setData(foundEvent);

			return new ResponseEntity<ResponseStructure<Event>>(response, HttpStatus.FOUND);
		} else
			throw new EventNotFoundException("Unable to find Event for the ID : " + id);
	}

	public ResponseEntity<ResponseStructure<Event>> updateEvent(Event event) {

		Event oldEvent = dao.findEventById(event.getId());

		if (oldEvent != null && event.getName() != null) {

			Event updateEvent = dao.saveEvent(event);

			ResponseStructure<Event> response = new ResponseStructure<Event>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Event Updated in the Database sucessfully");
			response.setData(updateEvent);
			return new ResponseEntity<ResponseStructure<Event>>(response, HttpStatus.OK);
		} else
			throw new EventNotFoundException("Missing the Attribute : " + event.getId() + " or : " + event.getName());
	}

	public ResponseEntity<ResponseStructure<Boolean>> DeleteEventById(int id) {

		Event foundEvent = dao.findEventById(id);

		if (foundEvent != null) {
			
			boolean flag = dao.deleteEvent(id) ;
			
			if(flag) {

				ResponseStructure<Boolean> response = new ResponseStructure<Boolean>();
				response.setStatusCode(HttpStatus.FOUND.value());
				response.setMessage("Event Found in the databse sucessfully");
				response.setData(true);
				return new ResponseEntity<ResponseStructure<Boolean>>(response, HttpStatus.CREATED);
				
			}
			else
				throw new EventNotFoundException("Unable to Delere Event for the ID : " + id);
		} else
			throw new EventNotFoundException("Unable to Delete Event for the ID : " + id);
	}
}
