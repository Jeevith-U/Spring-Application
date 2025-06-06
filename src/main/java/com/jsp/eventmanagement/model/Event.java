package com.jsp.eventmanagement.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event {

	@Id
	private int id ;
	private String name ;
	private String discription ;
	private String local ;
	private String date ;
}
