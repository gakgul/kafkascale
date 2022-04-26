package tr.com.ga.kafkasample.controller;


import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.com.ga.kafkasample.dto.PostAddDto;
import tr.com.ga.kafkasample.dto.PostDto;
import tr.com.ga.kafkasample.service.CommentService;
import tr.com.ga.kafkasample.service.PostService;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    //for permission based authentication add:
    //@PreAuthorize("hasRole('POSTS')")

    @GetMapping({"/{id}"})
    public ResponseEntity getById(@PathVariable Integer id) {
        var res = postService.getById(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping({"/{postId}/comments"})
    public ResponseEntity get(@PathVariable Integer postId) {
        var res = commentService.getAllCommentsByPostId(postId);
        return ResponseEntity.ok(res);
    }

    @PostMapping
    public ResponseEntity add(@RequestBody PostAddDto dto) {
        var res = postService.insertPost(dto);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable Integer id, @RequestBody PostDto dto) {
        var res = postService.update(dto);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patch(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        var res = postService.patch(id, patch);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        postService.delete(id);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping
    public ResponseEntity filter(@RequestParam Map<String, String> filters) {
        var res = postService.filter(filters);
        return ResponseEntity.ok(res);
    }
}