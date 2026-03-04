package com.phanish.urlshortner.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.PrimitiveIterator;

@Data
@Builder
public class UrlResponse
{
    private Long id;
    private String url;
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long accessCount;

}
