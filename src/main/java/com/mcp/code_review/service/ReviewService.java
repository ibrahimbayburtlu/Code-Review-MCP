package com.mcp.code_review.service;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.mcp.code_review.analyzer.JavaAnalyzer;
import com.mcp.code_review.model.ReviewIssue;
import com.mcp.code_review.model.ReviewResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JavaAnalyzer analyzer;

    public ReviewResult review(String path) {

        try {

            CompilationUnit compilationUnit = analyzer.parse(path);

            int classCount = compilationUnit
                    .findAll(ClassOrInterfaceDeclaration.class)
                    .size();

            return ReviewResult.builder()
                    .score(100)
                    .issues(List.of(
                            ReviewIssue.builder()
                                    .rule("INFO")
                                    .severity("LOW")
                                    .message("Successfully parsed Java file. Found "
                                            + classCount + " class(es).")
                                    .line(0)
                                    .build()
                    ))
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