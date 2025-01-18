package com.khalid.url_shortner.service;

import com.khalid.url_shortner.entity.UrlMapping;
import com.khalid.url_shortner.repo.UrlMappingRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository repository;

    public String shortenUrl(String longUrl) {
        String shortUrl = generateShortUrl(longUrl);
        UrlMapping mapping = new UrlMapping();
        mapping.setShortUrl(shortUrl);
        mapping.setLongUrl(longUrl);
        repository.save(mapping);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"))
                .getLongUrl();
    }

    private String generateShortUrl(String longUrl) {
        String uniqueKey = String.valueOf(longUrl.hashCode() + System.nanoTime());
        return Base64.getUrlEncoder().encodeToString(uniqueKey.getBytes());
    }

}
