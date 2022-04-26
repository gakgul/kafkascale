package tr.com.ga.kafkasample.entity.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import tr.com.ga.kafkasample.service.SequenceGeneratorService;
import tr.com.ga.kafkasample.entity.Post;

@Component
public class PostModelListener extends AbstractMongoEventListener<Post> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public PostModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Post> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Post.SEQUENCE_NAME));
        }
    }


}