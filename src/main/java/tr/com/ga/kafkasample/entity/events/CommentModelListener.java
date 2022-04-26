package tr.com.ga.kafkasample.entity.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import tr.com.ga.kafkasample.entity.Comment;
import tr.com.ga.kafkasample.service.SequenceGeneratorService;

@Component
public class CommentModelListener extends AbstractMongoEventListener<Comment> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public CommentModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Comment> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Comment.SEQUENCE_NAME));
        }
    }


}