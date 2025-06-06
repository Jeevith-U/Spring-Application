package com.jsp.eventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.eventmanagement.Response.ResponseStructure;
import com.jsp.eventmanagement.model.Event;
import com.jsp.eventmanagement.service.EventService;

@RestController
public class EventController {
	
	@Autowired
	private EventService service ;
	
	@PostMapping("event")
	public ResponseEntity<ResponseStructure<Event>> saveEvent(@RequestBody Event event) {
		return service.saveEvent(event);
	}
	
	@GetMapping("event/{id}")
	public ResponseEntity<ResponseStructure<Event>> findEventById(@PathVariable int id) {
		
		return service.findEventById(id) ;
	}
	
	@PutMapping("event")
	public ResponseEntity<ResponseStructure<Event>> updateEvent(@RequestBody Event event) {
		return service.updateEvent(event);
	}
	
	@DeleteMapping("event/{id}")
	public ResponseEntity<ResponseStructure<Boolean>> deleteEventById(@PathVariable int id) {
		
		return service.DeleteEventById(id) ;
	}
}
