package tr.com.ga.common.config.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@ToString
@Builder
public class EventDto<T> implements Serializable {

	private UUID id=UUID.randomUUID();
	private String name;
	private T data; // data of the event
	private Long creationDateInMillis=new Date().getTime();
	private int version=0;
	private String user;

	public EventDto(UUID id, String name, T data, int version) {
		this.id = id;
		this.name = name;
		this.data = data;
		this.version = version;
	}
	public EventDto(String ad) {
		this.name = ad;
	}

	public EventDto() {
		this.id=UUID.randomUUID();
		creationDateInMillis=new Date().getTime();
	}
	public EventDto(String name, T data) {
		this.name = name;
		this.data = data;
	}

	public EventDto(UUID id, String name, T data){
		this.id=id;
		this.name = name;
		this.data=data;
	}

}