package com.khalid.url_shortner.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnalyticsConsumer {
    private Map<String, Integer> accessCounts = new ConcurrentHashMap<>();

    @KafkaListener(topics = "url-access-events", groupId = "analytics")
    public void consume(String shortUrl) {
        accessCounts.put(shortUrl, accessCounts.getOrDefault(shortUrl, 0) + 1);
        System.out.println("URL Access Count: " + accessCounts);
    }

    public int getAccessCount(String shortUrl) {
        return accessCounts.getOrDefault(shortUrl, 0);
    }
}
