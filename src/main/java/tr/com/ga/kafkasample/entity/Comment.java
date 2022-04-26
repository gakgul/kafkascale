package tr.com.ga.kafkasample.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
@Document
public class Comment {

    @Transient
    public static final String SEQUENCE_NAME = "comment_sequence";

    @Id
    private Integer id;
    private Integer postId;
    private String name;
    private String email;
    private String body;
}
