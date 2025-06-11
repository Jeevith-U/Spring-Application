package com.jsp.eventmanagement.controller.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jsp.eventmanagement.Response.ResponseStructure;
import com.jsp.eventmanagement.dao.EventDao;
import com.jsp.eventmanagement.model.Event;
import com.jsp.eventmanagement.service.EventService;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
	
	@Mock
    private EventDao eventDao;

    @InjectMocks
    private EventService eventService;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setId(1);
        event.setName("Tech Talk");
        event.setDiscription("Java Conference");
        event.setLocal("Bangalore");
        event.setDate("2025-06-10");
    }
    
    @Test
    void testSaveEvent_Success() {
        when(eventDao.saveEvent(any(Event.class))).thenReturn(event);

        ResponseEntity<ResponseStructure<Event>> response = eventService.saveEvent(event);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Event saved to the databse sucessfully", response.getBody().getMessage());
    }

}
