package tr.com.ga.common.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * for kubernetes livenessProbe:
 */

@RestController
@RequestMapping("/health")
@Log4j2
public class HealthController {

    @GetMapping
    public ResponseEntity health() {
        return new ResponseEntity("Healthy", HttpStatus.OK);
    }

}