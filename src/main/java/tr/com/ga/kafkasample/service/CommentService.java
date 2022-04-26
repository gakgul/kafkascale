package tr.com.ga.kafkasample.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tr.com.ga.kafkasample.dto.CommentDto;
import tr.com.ga.kafkasample.entity.Comment;
import tr.com.ga.kafkasample.repository.CommentRepository;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CommentService {
    private final CommentRepository commentRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private final MongoTemplate mongoTemplate;


    public CommentService(CommentRepository commentRepository, MongoTemplate mongoTemplate) {
        this.commentRepository = commentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public CommentDto addComment(Comment data) {
        var res = commentRepository.save(data);
        return mapper.convertValue(res, CommentDto.class);
    }

    public List<CommentDto> getAllCommentsByPostId(Integer postId) {
        var res = commentRepository.findAllByPostId(postId);
        return mapper.convertValue(res, new TypeReference<>() {});
    }
    public List<CommentDto> getAll() {
        var res = commentRepository.findAll();
        return mapper.convertValue(res, new TypeReference<>() {});
    }
    public List<CommentDto> filter(Map<String, String> filters) {
        if(filters.isEmpty())
            return getAll();

        Query query = new Query();
        for(var key :filters.keySet()){
            var criteria = new Criteria();
            if("postId".equals(key) || "id".equals(key))
                criteria.and(key).is(Integer.parseInt(filters.get(key)));
            else
                criteria.and(key).is(filters.get(key));

            query.addCriteria(criteria);
        }
        var res =mongoTemplate.find(query, Comment.class);
        return mapper.convertValue(res, new TypeReference<>() {});
    }
}
