package tr.com.ga.kafkasample.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.com.ga.common.config.kafka.dto.EventDto;
import tr.com.ga.kafkasample.dto.CommentDto;
import tr.com.ga.kafkasample.entity.Comment;
import tr.com.ga.kafkasample.service.CommentService;

@Log4j2
@Component
public class CommentConsumer {

    private final CommentService commentService;
    private ObjectMapper mapper = new ObjectMapper();

    private EventDto<CommentDto> message = null;

    public CommentConsumer(CommentService commentService) {
        this.commentService = commentService;
    }


    @KafkaListener(topics = "${kafka.comment-topic}", containerFactory = "commentConsumerFactory")
    public void consume(EventDto<CommentDto> msg) {
        this.message=msg;
        log.debug(msg);
        var comment = mapper.convertValue(msg.getData(), Comment.class);
        commentService.addComment(comment);

    }

    //added for testing purpose
    public EventDto<CommentDto> message(){return this.message;}

}
