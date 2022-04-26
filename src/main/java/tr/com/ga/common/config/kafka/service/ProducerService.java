package tr.com.ga.common.config.kafka.service;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;
import tr.com.ga.common.config.kafka.dto.EventDto;

@Service
@Log4j2
public class ProducerService {

	private final KafkaTemplate<String, EventDto<?>> kafkaTemplate4Event;

	public ProducerService(KafkaTemplate<String, EventDto<?>> kafkaTemplate4Event) {
		this.kafkaTemplate4Event = kafkaTemplate4Event;
	}

	public <T> void sendMessage(String topicName, EventDto<T> msg){
		try {
			if(!StringUtils.hasLength(topicName)){
				throw new RuntimeException("topic name connot be empty");
			}
			else if(msg == null){
				throw new RuntimeException("message to be sent cannot be empty");
			}
			else if(!StringUtils.hasLength(msg.getName())){
				throw new RuntimeException("msg.name cannot be empty");
			}

			var future = kafkaTemplate4Event.send(topicName,msg);
			future.addCallback(new ListenableFutureCallback<>() {
				@SneakyThrows
				@Override
				public void onFailure(Throwable ex) {
					log.error("Unable to send message=[ {} ] due to : {}", msg, ex.getMessage());
					throw ex;
				}

				@Override
				public void onSuccess(SendResult<String, EventDto<?>> result) {
					log.debug("Sent message=[ {} ] with offset=[ {} ]", msg, result.getRecordMetadata().offset());
				}
			});
		}finally {
			kafkaTemplate4Event.flush();
		}
	}
}
