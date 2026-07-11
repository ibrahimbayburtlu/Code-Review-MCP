package com.mcp.code_review.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.mcp.code_review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MagicNumberRule implements Rule {

    private static final Set<String> ALLOWED_NUMBERS =
            Set.of("0", "1", "-1");

    @Override
    public List<ReviewIssue> analyze(CompilationUnit compilationUnit) {

        List<ReviewIssue> issues = new ArrayList<>();

        List<IntegerLiteralExpr> literals =
                compilationUnit.findAll(IntegerLiteralExpr.class);

        for (IntegerLiteralExpr literal : literals) {

            String value = literal.getValue();

            if (ALLOWED_NUMBERS.contains(value)) {
                continue;
            }

            issues.add(
                    ReviewIssue.builder()
                            .rule("MAGIC_NUMBER")
                            .severity("LOW")
                            .message("Magic number detected: " + value)
                            .line(literal.getBegin().map(p -> p.line).orElse(0))
                            .build()
            );
        }

        return issues;
    }
}