package tr.com.ga.kafkasample.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import tr.com.ga.kafkasample.service.CommentService;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //for permission based authentication add:
    //@PreAuthorize("hasRole('COMMENTS')")
    @GetMapping
    public ResponseEntity filter(@RequestParam Map<String, String> filters) {
        var res = commentService.filter(filters);
        return ResponseEntity.ok(res);
    }
}