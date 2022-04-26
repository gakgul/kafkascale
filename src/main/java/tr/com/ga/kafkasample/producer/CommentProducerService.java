package tr.com.ga.kafkasample.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tr.com.ga.common.config.kafka.dto.EventDto;
import tr.com.ga.common.config.kafka.service.ProducerService;
import tr.com.ga.kafkasample.dto.CommentDto;

import java.util.List;

@Service
public class CommentProducerService {

    private final ProducerService producerService;

    @Value("${kafka.comment-topic}")
    private String commentTopicName;

    public CommentProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }
    public void sendAll(List<CommentDto> list){
        list.stream().forEach(comment -> producerService.sendMessage(commentTopicName,generateEventDto(comment)));
    }
    private EventDto generateEventDto(CommentDto dto){
        var eventDto = new EventDto<CommentDto>();
        eventDto.setData(dto);
        eventDto.setName("COMMENT_ADDED");
        return eventDto;
    }
}
