package tr.com.ga.kafkasample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentAddDto {
    private Integer postId;
    private String name;
    private String email;
    private String body;
}
