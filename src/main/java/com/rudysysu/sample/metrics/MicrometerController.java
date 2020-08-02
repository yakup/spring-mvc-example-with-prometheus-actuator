package com.rudysysu.sample.metrics;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MicrometerController {
    private final PrometheusMeterRegistry prometheusMeterRegistry;

    public MicrometerController(PrometheusMeterRegistry prometheusMeterRegistry) {
        this.prometheusMeterRegistry = prometheusMeterRegistry;
    }

    @GetMapping("/micrometer")
    public ResponseEntity<String> micrometer() {
        return ResponseEntity.ok(prometheusMeterRegistry.scrape());
    }

    @GetMapping("/hello")
    @Timed("hello_time")
    @Counted("hello_count")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello!");
    }

    @GetMapping("/counted")
    @Counted("counted_count")
    public ResponseEntity<String> counted() {
        return ResponseEntity.ok("Counted!");
    }

    @GetMapping("/timed")
    @Timed("timed_time")
    public ResponseEntity<String> timed() {
        return ResponseEntity.ok("Timed!");
    }
}
