package tr.com.ga.kafkasample.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tr.com.ga.common.config.kafka.dto.EventDto;
import tr.com.ga.kafkasample.dto.PostDto;
import tr.com.ga.kafkasample.entity.Post;
import tr.com.ga.kafkasample.service.PostService;

@Log4j2
@Component
public class PostConsumer {

    private final PostService postService;
    private ObjectMapper mapper = new ObjectMapper();

    public PostConsumer(PostService postService) {
        this.postService = postService;
    }

    @KafkaListener(topics = "${kafka.post-topic}", containerFactory = "postConsumerFactory")
    protected void consume(EventDto<PostDto> msg) {
        var post = mapper.convertValue(msg.getData(), Post.class);
        postService.addPostFromConsumer(post);
    }

}
