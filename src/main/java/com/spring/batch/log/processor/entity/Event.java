package com.spring.batch.log.processor.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	private String id;
	private String host;
	private String type;
	private Long timestamp;
	private boolean alert;


	@Override
	public String toString() {
		return "Event [id=" + id + ", host=" + host + ", type=" + type + ", duration=" + timestamp + ", alert=" + alert
				+ "]";
	}

}
