package com.phanish.urlshortner.service;

import com.phanish.urlshortner.dto.UrlRequest;
import com.phanish.urlshortner.dto.UrlResponse;
import com.phanish.urlshortner.entity.UrlMapping;
import com.phanish.urlshortner.repository.UrlRepository;
import com.phanish.urlshortner.util.ShortCodeGenerator;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Override
    public UrlResponse createShortUrl(UrlRequest request) {

        // Ensure URL has http or https
        String originalUrl = request.getUrl();
        if (!originalUrl.startsWith("http")) {
            originalUrl = "https://" + originalUrl;
        }

        String shortCode;

        // Custom short code
        if (request.getCustomCode() != null && !request.getCustomCode().isEmpty()) {

            if (urlRepository.existsByShortCode(request.getCustomCode())) {
                throw new RuntimeException("Custom code already exists");
            }

            shortCode = request.getCustomCode();

        } else {

            // Random short code
            do {
                shortCode = ShortCodeGenerator.generateShortCode();
            } while (urlRepository.existsByShortCode(shortCode));
        }

        UrlMapping mapping = UrlMapping.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .accessCount(0L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        UrlMapping saved = urlRepository.save(mapping);

        return UrlResponse.builder()
                .id(saved.getId())
                .url(saved.getOriginalUrl())
                .shortCode(saved.getShortCode())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .accessCount(saved.getAccessCount())
                .build();
    }

    @Override
    public UrlResponse getOriginalUrl(String shortCode) {

        UrlMapping mapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        mapping.setAccessCount(mapping.getAccessCount() + 1);
        urlRepository.save(mapping);

        return UrlResponse.builder()
                .id(mapping.getId())
                .url(mapping.getOriginalUrl())
                .shortCode(mapping.getShortCode())
                .createdAt(mapping.getCreatedAt())
                .updatedAt(mapping.getUpdatedAt())
                .accessCount(mapping.getAccessCount())
                .build();
    }

    @Override
    public UrlResponse updateUrl(String shortCode, UrlRequest request) {

        UrlMapping mapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        mapping.setOriginalUrl(request.getUrl());
        mapping.setUpdatedAt(LocalDateTime.now());

        urlRepository.save(mapping);

        return UrlResponse.builder()
                .id(mapping.getId())
                .url(mapping.getOriginalUrl())
                .shortCode(mapping.getShortCode())
                .createdAt(mapping.getCreatedAt())
                .updatedAt(mapping.getUpdatedAt())
                .accessCount(mapping.getAccessCount())
                .build();
    }

    @Override
    public void deleteUrl(String shortCode) {

        UrlMapping mapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        urlRepository.delete(mapping);
    }

    @Override
    public UrlResponse getStats(String shortCode) {

        UrlMapping mapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        return UrlResponse.builder()
                .id(mapping.getId())
                .url(mapping.getOriginalUrl())
                .shortCode(mapping.getShortCode())
                .createdAt(mapping.getCreatedAt())
                .updatedAt(mapping.getUpdatedAt())
                .accessCount(mapping.getAccessCount())
                .build();
    }
}