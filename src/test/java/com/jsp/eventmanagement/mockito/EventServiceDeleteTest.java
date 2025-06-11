package com.jsp.eventmanagement.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class EventServiceDeleteTest {
	
	@Mock
	private EventDao eventDao ;
	
	@InjectMocks
	private EventService eventService ;
	
	private Event event ;
	
	@BeforeEach
	void setups() {
		MockitoAnnotations.openMocks(this) ;
        event = new Event(1, "Wedding", "Wedding Ceremony", "Paris", "10/10/2050");
	}
	
	@Test
	void successfullDeletion() {
		
		when(eventDao.findEventById(event.getId())).thenReturn(event) ;
		when(eventDao.deleteEvent(event.getId())).thenReturn(true) ;
		
		ResponseEntity<ResponseStructure<Boolean>> respone = eventService.DeleteEventById(1) ;
		
		assertEquals(HttpStatus.OK, respone.getStatusCode());
		assertEquals("Event deleted from the databse sucessfully", respone.getBody().getMessage());
		assertEquals(true, respone.getBody().getData());
		verify(eventDao, times(1)).deleteEvent(event.getId()) ;
	}
	
	@Test
	void failurefullDeletion() {
		
		
		when(eventDao.findEventById(99)).thenReturn(null) ;
		
		EventNotFoundException exception = assertThrows(EventNotFoundException.class, () -> eventService.DeleteEventById(99) ) ;
		
		assertEquals("Unable to Delete Event for the ID : 99", exception.getMessage());
        verify(eventDao, times(1)).findEventById(99);
        verify(eventDao, times(0)).deleteEvent(99);
	}

}
