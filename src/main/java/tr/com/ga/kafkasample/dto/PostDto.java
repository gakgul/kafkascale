package tr.com.ga.kafkasample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
