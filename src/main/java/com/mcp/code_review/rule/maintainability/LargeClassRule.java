package com.mcp.code_review.rule.maintainability;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.mcp.code_review.model.ReviewIssue;
import com.mcp.code_review.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LargeClassRule implements Rule {

    private static final int MAX_CLASS_LINES = 500;

    @Override
    public List<ReviewIssue> analyze(CompilationUnit compilationUnit) {

        List<ReviewIssue> issues = new ArrayList<>();

        List<ClassOrInterfaceDeclaration> classes =
                compilationUnit.findAll(ClassOrInterfaceDeclaration.class);

        for (ClassOrInterfaceDeclaration clazz : classes) {

            if (clazz.getBegin().isEmpty() || clazz.getEnd().isEmpty()) {
                continue;
            }

            int lineCount =
                    clazz.getEnd().get().line -
                            clazz.getBegin().get().line + 1;

            if (lineCount > MAX_CLASS_LINES) {

                issues.add(
                        ReviewIssue.builder()
                                .rule("LARGE_CLASS")
                                .severity("MEDIUM")
                                .message(String.format(
                                        "Class '%s' has %d lines (max %d).",
                                        clazz.getNameAsString(),
                                        lineCount,
                                        MAX_CLASS_LINES))
                                .line(clazz.getBegin().get().line)
                                .build()
                );

            }

        }

        return issues;
    }
}