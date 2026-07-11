package com.mcp.code_review.service;

import com.mcp.code_review.model.ReviewIssue;
import com.mcp.code_review.model.ReviewResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    public ReviewResult review(String path) {

        return ReviewResult.builder()
                .score(90)
                .issues(List.of(
                        ReviewIssue.builder()
                                .rule("LONG_METHOD")
                                .severity("MEDIUM")
                                .message("login() method has 55 lines")
                                .line(42)
                                .build()
                ))
                .build();

    }

}