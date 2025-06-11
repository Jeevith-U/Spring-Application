package com.jsp.eventmanagement.controller.mockito;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.eventmanagement.Response.ResponseStructure;
import com.jsp.eventmanagement.model.Event;
import com.jsp.eventmanagement.service.EventService;

@WebMvcTest()
public class EventControllerTestSave  {

	@Autowired
	private MockMvc mockMvc ;
	
	@MockBean
    private EventService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Event event;
    private ResponseStructure<Event> responseStructure;
    
    @BeforeEach
    void setup() {
        event = new Event(1, "Marriage", "Someones Marriage", "Scotland", "20/02/2050");
        responseStructure = new ResponseStructure<>();
        responseStructure.setStatusCode(HttpStatus.CREATED.value());
        responseStructure.setMessage("Event saved to the databse sucessfully");
        responseStructure.setData(event);
    }
    
    @Test
    void testSaveEvent() throws Exception {
        when(service.saveEvent(Mockito.any(Event.class)))
                .thenReturn(new ResponseEntity<>(responseStructure, HttpStatus.CREATED));

        mockMvc.perform(post("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Event saved to the databse sucessfully"))
                .andExpect(jsonPath("$.data.name").value("Marriage"))
                .andExpect(jsonPath("$.data.local").value("Scotland"));

    }
    
    @Test
    void testFindEventById() throws Exception {
        responseStructure.setMessage("Event Found in the databse sucessfully");

        when(service.findEventById(1))
                .thenReturn(new ResponseEntity<>(responseStructure, HttpStatus.FOUND));

        mockMvc.perform(get("/event/1"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Marriage"));
    }

    
    @Test
    void testUpdateEvent() throws Exception {
        responseStructure.setMessage("Event Updated in the Database sucessfully");

        when(service.updateEvent(Mockito.any(Event.class)))
                .thenReturn(new ResponseEntity<>(responseStructure, HttpStatus.OK));

        mockMvc.perform(put("/event")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Marriage"))
                .andExpect(jsonPath("$.message").value("Event Updated in the Database sucessfully"));
    }

    @Test
    void testDeleteEventById() throws Exception {
        ResponseStructure<Boolean> deleteResponse = new ResponseStructure<>();
        deleteResponse.setStatusCode(HttpStatus.CREATED.value());
        deleteResponse.setMessage("Event Found in the databse sucessfully");
        deleteResponse.setData(true);

        when(service.DeleteEventById(1))
                .thenReturn(new ResponseEntity<>(deleteResponse, HttpStatus.CREATED));

        mockMvc.perform(delete("/event/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data").value(true));
    }

    
    
    
}
