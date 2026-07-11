package com.mcp.code_review.rule.reliability;


import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.mcp.code_review.model.ReviewIssue;
import com.mcp.code_review.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmptyMethodRule implements Rule {

    @Override
    public List<ReviewIssue> analyze(CompilationUnit compilationUnit) {

        List<ReviewIssue> issues = new ArrayList<>();

        List<MethodDeclaration> methods =
                compilationUnit.findAll(MethodDeclaration.class);

        for (MethodDeclaration method : methods) {

            if (method.isAbstract()) {
                continue;
            }

            if (method.getBody().isEmpty()) {
                continue;
            }

            if (!method.getBody().get().getStatements().isEmpty()) {
                continue;
            }

            issues.add(
                    ReviewIssue.builder()
                            .rule("EMPTY_METHOD")
                            .severity("MEDIUM")
                            .message(String.format(
                                    "Method '%s' has an empty body.",
                                    method.getNameAsString()))
                            .line(method.getBegin()
                                    .map(position -> position.line)
                                    .orElse(0))
                            .build()
            );
        }

        return issues;
    }
}