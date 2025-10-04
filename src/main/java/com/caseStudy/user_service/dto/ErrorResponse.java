package com.caseStudy.user_service.dto;

import java.time.Instant;

public record ErrorResponse(String code, String message, String path, Instant timestamp) {

}
