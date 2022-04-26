package tr.com.ga.kafkasample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tr.com.ga.common.config.kafka.service.ProducerService;
import tr.com.ga.kafkasample.dto.CommentDto;
import tr.com.ga.kafkasample.dto.PostDto;
import tr.com.ga.kafkasample.producer.CommentProducerService;
import tr.com.ga.kafkasample.producer.PostProducerService;

import java.time.Duration;
import java.util.List;

@Service
public class InitService {
    WebClient webClient;
    private final CommentProducerService commentProducerService;
    private final PostProducerService postProducerService;

    public InitService(ProducerService producerService, CommentProducerService commentProducerService, PostProducerService postProducerService) {
        this.webClient = WebClient.create();
        this.commentProducerService = commentProducerService;
        this.postProducerService = postProducerService;
    }

    public void init(){
        readPosts();
        readComments();
    }


    private void readComments() {
        var comments = webClient.get()
                .uri("https://jsonplaceholder.typicode.com/comments")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CommentDto>>(){})
                .blockOptional(Duration.ofSeconds(10))
                .get();
        commentProducerService.sendAll(comments);
    }

    private void readPosts() {
        var posts = webClient.get()
                .uri("https://jsonplaceholder.typicode.com/posts")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PostDto>>(){})
                .blockOptional(Duration.ofSeconds(10))
                .get();
        postProducerService.sendAll(posts);
    }
}
