package tr.com.ga.kafkasample.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@NoArgsConstructor
@Document
@AllArgsConstructor
@Builder
public class Post {

    @Transient
    public static final String SEQUENCE_NAME = "post_sequence";

    @Id
    private Integer id;
    private String title;
    private String body;
    private Integer userId;
}
