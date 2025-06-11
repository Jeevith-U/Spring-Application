package com.jsp.eventmanagement.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

public class EventServiceUpdateTest {
	
	@Mock
	private EventDao dao ;
	
	@InjectMocks
	private EventService eventService ;
	
	private Event existingEvent;
    private Event updatedEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        existingEvent = new Event(1, "Old Title", "Old Description", "Delhi", "2025-01-01");
        updatedEvent = new Event(1, "Updated Title", "Updated Description", "Mumbai", "2025-06-10");
    }

    @Test
    void updateEventSuccess() {

    	when(dao.findEventById(1)).thenReturn(existingEvent);
        when(dao.saveEvent(updatedEvent)).thenReturn(updatedEvent);
        

        ResponseEntity<ResponseStructure<Event>> response = eventService.updateEvent(updatedEvent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Event Updated in the Database sucessfully", response.getBody().getMessage());
        assertEquals(updatedEvent.getName(), response.getBody().getData().getName());
        assertEquals(updatedEvent.getDiscription(), response.getBody().getData().getDiscription());

        verify(dao, times(1)).findEventById(1);
        verify(dao, times(1)).saveEvent(updatedEvent);
    }
    
    @Test
    void updateEventFailure() {

    	when(dao.findEventById(1)).thenReturn(null);
    	
        Event eventToUpdate = new Event(99, "Ghost Event", "Doesn't exist", "Nowhere", "2025-01-01");

        EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(eventToUpdate)) ;

        assertEquals("Missing the Attribute : 99 or : Ghost Event", exception.getMessage());
        verify(dao, times(1)).findEventById(99);
        verify(dao, never()).saveEvent(any(Event.class));
    }

}
