package tr.com.ga.kafkasample.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.com.ga.kafkasample.entity.Post;

@Repository
public interface PostRepository extends MongoRepository<Post,Integer> {
}
