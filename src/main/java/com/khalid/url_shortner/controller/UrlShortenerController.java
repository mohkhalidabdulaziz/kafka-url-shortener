package com.khalid.url_shortner.controller;

import com.khalid.url_shortner.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {
    @Autowired
    private UrlShortenerService service;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "url-access-events";

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String longUrl) {
        String shortUrl = service.shortenUrl(longUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String longUrl = service.getLongUrl(shortUrl);
        kafkaTemplate.send(TOPIC, shortUrl);
        response.sendRedirect(longUrl);
        return ResponseEntity.status(HttpServletResponse.SC_MOVED_TEMPORARILY).build();
    }
}

