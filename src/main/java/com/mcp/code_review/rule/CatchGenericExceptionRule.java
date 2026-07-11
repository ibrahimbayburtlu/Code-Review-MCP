package com.mcp.code_review.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.CatchClause;
import com.mcp.code_review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CatchGenericExceptionRule implements Rule {

    @Override
    public List<ReviewIssue> analyze(CompilationUnit compilationUnit) {

        List<ReviewIssue> issues = new ArrayList<>();

        List<CatchClause> catches =
                compilationUnit.findAll(CatchClause.class);

        for (CatchClause catchClause : catches) {

            String exceptionType =
                    catchClause.getParameter()
                            .getType()
                            .asString();

            if ("Exception".equals(exceptionType)
                    || "Throwable".equals(exceptionType)) {

                issues.add(
                        ReviewIssue.builder()
                                .rule("CATCH_GENERIC_EXCEPTION")
                                .severity("HIGH")
                                .message("Avoid catching generic " + exceptionType + ".")
                                .line(catchClause.getBegin()
                                        .map(position -> position.line)
                                        .orElse(0))
                                .build()
                );
            }
        }

        return issues;
    }
}