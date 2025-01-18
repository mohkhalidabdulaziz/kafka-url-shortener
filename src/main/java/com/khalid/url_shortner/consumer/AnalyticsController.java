package com.khalid.url_shortner.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsConsumer consumer;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Integer> getAccessCount(@PathVariable String shortUrl) {
        int count = consumer.getAccessCount(shortUrl);
        return ResponseEntity.ok(count);
    }
}

