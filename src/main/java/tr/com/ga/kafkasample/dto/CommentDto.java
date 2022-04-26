package tr.com.ga.kafkasample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CommentDto {
    private Integer id;
    private Integer postId;
    private String name;
    private String email;
    private String body;
}
