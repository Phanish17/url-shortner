package com.phanish.urlshortner.service;

import com.phanish.urlshortner.dto.UrlRequest;
import com.phanish.urlshortner.dto.UrlResponse;

public interface UrlService
{
    UrlResponse createShortUrl(UrlRequest request);
    UrlResponse getOriginalUrl(String shortCode);
    UrlResponse updateUrl(String shortCode, UrlRequest request);

    void deleteUrl(String shortCode);

    UrlResponse getStats(String shortCode);
}
