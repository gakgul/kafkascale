package tr.com.ga.kafkasample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.com.ga.kafkasample.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment,Integer> {
    List<Comment> findAllByPostId(Integer postId);
}
