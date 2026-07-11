package com.mcp.code_review.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ReviewResult(
        int score,
        List<ReviewIssue> issues
) {
}