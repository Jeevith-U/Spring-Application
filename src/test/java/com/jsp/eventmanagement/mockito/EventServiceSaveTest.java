package com.jsp.eventmanagement.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.jsp.eventmanagement.Exception.UnableToSaveEventException;
import com.jsp.eventmanagement.Response.ResponseStructure;
import com.jsp.eventmanagement.dao.EventDao;
import com.jsp.eventmanagement.model.Event;
import com.jsp.eventmanagement.service.EventService;

public class EventServiceSaveTest {

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEventSuccess() {

    	Event event = new Event();
        event.setId(1);
        event.setName("Tech Meetup");
        event.setDiscription("Annual Tech Event");
        event.setLocal("Bangalore");
        event.setDate("2025-06-15");

        when(eventDao.saveEvent(any(Event.class))).thenReturn(event);

        ResponseEntity<ResponseStructure<Event>> response = eventService.saveEvent(event);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Event saved to the databse sucessfully", response.getBody().getMessage());
        assertEquals(event.getName(), response.getBody().getData().getName());

        verify(eventDao, times(1)).saveEvent(event);
    }

    @Test
    void testSaveEventThrowsExceptionWhenInvalid() {
        Event event = new Event();
        event.setId(0);
        event.setName(null); 
        event.setDiscription(null); 

        Exception exception = assertThrows(UnableToSaveEventException.class, () -> {
            eventService.saveEvent(event);
        });

        assertEquals("Missing attributes...", exception.getMessage());
        verify(eventDao, never()).saveEvent(any());
    }
}
