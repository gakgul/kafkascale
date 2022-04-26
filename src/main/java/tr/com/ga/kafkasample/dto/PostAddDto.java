package tr.com.ga.kafkasample.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostAddDto {
    private String title;
    private String body;
    private Integer userId;
}
