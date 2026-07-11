package com.mcp.code_review.rule.reliability;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.CatchClause;
import com.mcp.code_review.model.ReviewIssue;
import com.mcp.code_review.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmptyCatchRule implements Rule {

    @Override
    public List<ReviewIssue> analyze(CompilationUnit compilationUnit) {

        List<ReviewIssue> issues = new ArrayList<>();

        List<CatchClause> catches =
                compilationUnit.findAll(CatchClause.class);

        for (CatchClause catchClause : catches) {

            if (catchClause.getBody().isEmpty()) {

                issues.add(
                        ReviewIssue.builder()
                                .rule("EMPTY_CATCH")
                                .severity("MEDIUM")
                                .message("Empty catch block detected.")
                                .line(catchClause.getBegin()
                                        .map(p -> p.line)
                                        .orElse(0))
                                .build()
                );

            }

        }

        return issues;

    }

}