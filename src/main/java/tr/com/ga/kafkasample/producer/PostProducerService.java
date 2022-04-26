package tr.com.ga.kafkasample.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tr.com.ga.common.config.kafka.dto.EventDto;
import tr.com.ga.common.config.kafka.service.ProducerService;
import tr.com.ga.kafkasample.dto.PostDto;

import java.util.List;
import java.util.UUID;

@Service
public class PostProducerService {

    private final ProducerService producerService;

    @Value("${kafka.post-topic}")
    private String postTopicName;

    public PostProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }
    public void sendAll(List<PostDto> list){
        list.stream().forEach(post -> producerService.sendMessage(postTopicName,generateEventDto(post)));
    }
    private EventDto generateEventDto(PostDto dto){
        var eventDto = new EventDto<PostDto>();
        eventDto.setData(dto);
        eventDto.setName("POST_ADDED");
        return eventDto;
    }
}
