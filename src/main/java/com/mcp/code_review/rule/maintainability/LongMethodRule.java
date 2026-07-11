package com.mcp.code_review.rule.maintainability;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.mcp.code_review.model.ReviewIssue;
import com.mcp.code_review.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LongMethodRule implements Rule {

    private static final int MAX_METHOD_LINES = 40;

    @Override
    public List<ReviewIssue> analyze(CompilationUnit compilationUnit) {

        List<ReviewIssue> issues = new ArrayList<>();

        List<MethodDeclaration> methods =
                compilationUnit.findAll(MethodDeclaration.class);

        for (MethodDeclaration method : methods) {

            if (method.getBegin().isEmpty() || method.getEnd().isEmpty()) {
                continue;
            }

            int lineCount =
                    method.getEnd().get().line -
                            method.getBegin().get().line + 1;

            if (lineCount > MAX_METHOD_LINES) {

                issues.add(
                        ReviewIssue.builder()
                                .rule("LONG_METHOD")
                                .severity("MEDIUM")
                                .message(String.format(
                                        "Method '%s' has %d lines (max %d).",
                                        method.getNameAsString(),
                                        lineCount,
                                        MAX_METHOD_LINES))
                                .line(method.getBegin().get().line)
                                .build()
                );
            }
        }

        return issues;
    }
}