package com.jsp.eventmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public ResponseEntity<ResponseStructure<Event>> saveEvent(Event event) {

        logger.info("Attempting to save event: {}", event);

        if (event.getId() != 0 && event.getName() != null && event.getDiscription() != null) {

            Event savedEvent = dao.saveEvent(event);

            logger.debug("Event saved successfully: {}", savedEvent);

            ResponseStructure<Event> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setMessage("Event saved to the database successfully");
            response.setData(savedEvent);

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } else {
            logger.error("Unable to save event - Missing required fields: {}", event);
            throw new UnableToSaveEventException("Missing attributes...");
        }
    }

    @Cacheable(value = "events", key = "#id")
    public Event findEventById(int id) {

        logger.info("Attempting to retrieve event with ID: {}", id);

        Event foundEvent = dao.findEventById(id);

        if (foundEvent != null) {
            logger.debug("Event retrieved successfully from DB (and now cached): {}", foundEvent);
            return foundEvent;
        } else {
            logger.warn("Event with ID {} not found in DB", id);
            throw new EventNotFoundException("Unable to find Event for ID: " + id);
        }
    }

    @CachePut(value = "events", key = "#event.id")
    public ResponseEntity<ResponseStructure<Event>> updateEvent(Event event) {

        logger.info("Attempting to update event with ID: {}", event.getId());

        Event oldEvent = dao.findEventById(event.getId());

        if (oldEvent != null && event.getName() != null) {

            Event updatedEvent = dao.saveEvent(event);

            logger.debug("Event updated successfully: {}", updatedEvent);

            ResponseStructure<Event> response = new ResponseStructure<>();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Event updated in the database successfully");
            response.setData(updatedEvent);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            logger.warn("Update failed - Event not found or invalid input: {}", event);
            throw new EventNotFoundException("Missing the Attribute : " + event.getId() + " or : " + event.getName());
        }
    }

    @CacheEvict(value = "events", key = "#id")
    public ResponseEntity<ResponseStructure<Boolean>> DeleteEventById(int id) {

        logger.info("Attempting to delete event with ID: {}", id);

        Event foundEvent = dao.findEventById(id);

        if (foundEvent != null) {

            boolean isDeleted = dao.deleteEvent(id);

            if (isDeleted) {
                logger.debug("Event with ID {} deleted from DB and cache evicted", id);

                ResponseStructure<Boolean> response = new ResponseStructure<>();
                response.setStatusCode(HttpStatus.OK.value());
                response.setMessage("Event deleted from the database successfully");
                response.setData(true);

                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                logger.error("Failed to delete event with ID {} from DB", id);
                throw new EventNotFoundException("Unable to delete Event for the ID: " + id);
            }

        } else {
            logger.warn("Delete failed - Event not found for ID: {}", id);
            throw new EventNotFoundException("Unable to delete Event for the ID: " + id);
        }
    }
}
