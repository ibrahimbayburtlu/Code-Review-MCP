package com.mcp.code_review.model;

import lombok.Builder;

@Builder
public record ReviewIssue(
        String rule,
        String severity,
        String message,
        int line
) {
}