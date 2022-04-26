/*
package tr.com.ga.kafkasample;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import tr.com.ga.kafkasample.consumer.CommentConsumer;
import tr.com.ga.kafkasample.dto.CommentDto;
import tr.com.ga.kafkasample.producer.CommentProducerService;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(properties="spring.main.lazy-initialization=true")
@DirtiesContext
@EmbeddedKafka
@ActiveProfiles("test")
//@ComponentScan(basePackages = "tr.com.ga.common.config.kafka")
@DataMongoTest(properties="spring.main.lazy-initialization=true")
public class SimpleKafkaMessageTest {
    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private MongodExecutable mongodExecutable;
    private MongoTemplate mongoTemplate;

    @AfterEach
    void clean() {
        mongodExecutable.stop();
    }

    @Value("${kafka.topic.simpleMessageTopic}")
    private String topicName;

    @Autowired
    private CommentProducerService commentProducerService;

    @Autowired
    private CommentConsumer commentConsumer;

    @BeforeEach
    protected void prepare(){
        this.webClient = WebClient.create();
    }
    @BeforeEach
    void setup() throws Exception {
        String ip = "localhost";
        int port = 27017;

        ImmutableMongodConfig mongodConfig = MongodConfig
                .builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
    }

    private WebClient webClient;
    private void readCommentsAndSentToKafka() {
        var comments = webClient.get()
                .uri("https://jsonplaceholder.typicode.com/comments")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CommentDto>>(){})
                .blockOptional(Duration.ofSeconds(10))
                .get();
        commentProducerService.sendAll(comments);
    }

    @Test
    public void testSendReceive(EmbeddedKafkaBroker broker) throws Exception {
        readCommentsAndSentToKafka();
        TimeUnit.SECONDS.sleep(1);

        var msg = commentConsumer.message();
    }
    @Test
    public void test(@Autowired MongoTemplate mongoTemplate) {
        // given
        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();

        // when
        mongoTemplate.save(objectToSave, "collection");

        // then
        Assertions.assertNotNull(mongoTemplate.findAll(DBObject.class, "collection"));
    }
}

 */