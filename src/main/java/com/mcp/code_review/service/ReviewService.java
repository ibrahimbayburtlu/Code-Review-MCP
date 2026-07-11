package com.mcp.code_review.service;

import com.github.javaparser.ast.CompilationUnit;
import com.mcp.code_review.analyzer.JavaAnalyzer;
import com.mcp.code_review.model.ReviewIssue;
import com.mcp.code_review.model.ReviewResult;
import com.mcp.code_review.rule.LongMethodRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JavaAnalyzer analyzer;

    private final LongMethodRule longMethodRule;

    public ReviewResult review(String path) {

        try {

            CompilationUnit compilationUnit = analyzer.parse(path);

            List<ReviewIssue> issues = longMethodRule.analyze(compilationUnit);

            int score = Math.max(0, 100 - (issues.size() * 10));

            return ReviewResult.builder()
                    .score(score)
                    .issues(issues)
                    .build();

        } catch (IOException e) {

            return ReviewResult.builder()
                    .score(0)
                    .issues(List.of(
                            ReviewIssue.builder()
                                    .rule("FILE_ERROR")
                                    .severity("HIGH")
                                    .message(e.getMessage())
                                    .line(0)
                                    .build()
                    ))
                    .build();
        }
    }
}