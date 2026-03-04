package com.phanish.urlshortner.controller;

import com.phanish.urlshortner.dto.UrlRequest;
import com.phanish.urlshortner.dto.UrlResponse;
import com.phanish.urlshortner.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/shorten")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlResponse> create(@Valid @RequestBody UrlRequest request) {
        UrlResponse response = urlService.createShortUrl(request);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<UrlResponse> update(
            @PathVariable String shortCode,
            @Valid @RequestBody UrlRequest request) {

        return ResponseEntity.ok(urlService.updateUrl(shortCode, request));
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> delete(@PathVariable String shortCode) {
        urlService.deleteUrl(shortCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<UrlResponse> stats(@PathVariable String shortCode) {
        return ResponseEntity.ok(urlService.getStats(shortCode));
    }
}