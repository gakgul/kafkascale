package tr.com.ga.kafkasample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import tr.com.ga.kafkasample.dto.PostAddDto;
import tr.com.ga.kafkasample.dto.PostDto;
import tr.com.ga.kafkasample.repository.PostRepository;
import tr.com.ga.kafkasample.entity.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class PostService {
    private final PostRepository postRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoTemplate mongoTemplate;

    public PostService(PostRepository postRepository, SequenceGeneratorService sequenceGeneratorService, MongoTemplate mongoTemplate) {
        this.postRepository = postRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.mongoTemplate = mongoTemplate;
    }

    public PostDto addPostFromConsumer(Post data) {
        var res = postRepository.save(data);
        return mapper.convertValue(res,PostDto.class);
    }

    public List<PostDto> getAll() {
        var res = postRepository.findAll();
        return mapper.convertValue(res, new TypeReference<>() {});
    }

    public PostDto getById(Integer id) {
        var res = postRepository.findById(id).orElse(null);
        return mapper.convertValue(res, PostDto.class);
    }
    @Synchronized
    private Integer getNextPostId(){
        var nextId = sequenceGeneratorService.generateSequence(Post.SEQUENCE_NAME);
        while(postRepository.findById(nextId).orElse(null) != null)
            nextId = sequenceGeneratorService.generateSequence(Post.SEQUENCE_NAME);
        return nextId;
    }
    public PostDto insertPost(PostAddDto dto) {
        var post = mapper.convertValue(dto,Post.class);
        post.setId(getNextPostId());
        var res = postRepository.save(post);
        return mapper.convertValue(res, PostDto.class);
    }

    public PostDto update(PostDto dto) {
        var post = postRepository.findById(dto.getId()).orElse(null);
        if(post == null)
            return null;
        var postDummy = mapper.convertValue(post,PostDto.class);
        postDummy.setBody(dto.getBody());
        postDummy.setTitle(dto.getTitle());
        postDummy.setUserId(dto.getUserId());

        return postDummy;
    }

    @SneakyThrows
    public PostDto patch(Integer id, JsonPatch patch) {
        var post = postRepository.findById(id).orElse(null);
        if(post == null)
            return null;

        var postPatched = applyPatchToCustomer(patch,post);

        return mapper.convertValue(postPatched, PostDto.class);
    }

    private Post applyPatchToCustomer(JsonPatch patch, Post target) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(mapper.convertValue(target, JsonNode.class));
        return mapper.treeToValue(patched, Post.class);
    }

    public void delete(Integer id) {
        //postRepository.deleteById(id);
    }

    public List<PostDto> filter(Map<String, String> filters) {
        if(filters.isEmpty())
            return getAll();

        Query query = new Query();
        for(var key :filters.keySet()){
            var criteria = new Criteria();
            if("userId".equals(key) || "id".equals(key))
                criteria.and(key).is(Integer.parseInt(filters.get(key)));
            else
                criteria.and(key).is(filters.get(key));

            query.addCriteria(criteria);
        }
        var res =mongoTemplate.find(query,Post.class);
        return mapper.convertValue(res, new TypeReference<>() {});
    }
}
