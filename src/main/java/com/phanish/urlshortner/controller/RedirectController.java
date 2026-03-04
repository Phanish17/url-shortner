package com.phanish.urlshortner.controller;


import com.phanish.urlshortner.dto.UrlResponse;
import com.phanish.urlshortner.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final UrlService urlService;

    @GetMapping("/{shortCode:[a-zA-Z0-9]+}")
    public void redirect(@PathVariable String shortCode,
                         HttpServletResponse response) throws IOException {

        UrlResponse urlResponse = urlService.getOriginalUrl(shortCode);

        String originalUrl = urlResponse.getUrl();

        if (!originalUrl.startsWith("http")) {
            originalUrl = "https://" + originalUrl;
        }

        response.sendRedirect(originalUrl);
    }
}
