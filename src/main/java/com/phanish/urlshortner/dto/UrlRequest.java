package com.phanish.urlshortner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data

public class UrlRequest
{
    @NotBlank(message = "URL cannot be Empty :)")
    private String url;
    private String customCode;
}
