package com.jsp.eventmanagement.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jsp.eventmanagement.Exception.EventNotFoundException;
import com.jsp.eventmanagement.Response.ResponseStructure;
import com.jsp.eventmanagement.dao.EventDao;
import com.jsp.eventmanagement.model.Event;
import com.jsp.eventmanagement.service.EventService;

public class EventServiceFindTest {
	
	@Mock
	private EventDao dao ;
	
	@InjectMocks
	private EventService eventService ;
	
	private Event event ;
	
	 @BeforeEach
	    void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	 
	 @Test
	 void findEventByIDSuccess() {
	     
	     event = new Event(1, "Marriage", "Someones Marriage", "Scotland", "20/02/2050");
	     
	     when(dao.findEventById(event.getId())).thenReturn(event);
	     
	     ResponseEntity<ResponseStructure<Event>> eventResp = eventService.findEventById(event.getId());
	     
	     assertEquals(HttpStatus.FOUND, eventResp.getStatusCode());
	     assertEquals("Event Found in the databse sucessfully", eventResp.getBody().getMessage());
	     assertNotNull(eventResp.getBody().getData());
	     assertEquals(1, eventResp.getBody().getData().getId());
	     
	     verify(dao, times(1)).findEventById(event.getId());
	 }

	 
	 @Test
	    void testFindEventByIdNotFound() {
	        // Arrange
	        when(dao.findEventById(99)).thenReturn(null);

	        // Act & Assert
	        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> {
	            eventService.findEventById(99);
	        });

	        assertEquals("Unable to find Event for the ID : 99", exception.getMessage());
	        verify(dao, times(1)).findEventById(99);
	    }

}
