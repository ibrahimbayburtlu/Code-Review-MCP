package com.mcp.code_review.rule;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;
import com.mcp.code_review.model.ReviewIssue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TodoCommentRule implements Rule {

    private static final Map<String, String> COMMENT_TYPES = Map.of(
            "TODO", "TODO_COMMENT",
            "FIXME", "FIXME_COMMENT",
            "HACK", "HACK_COMMENT",
            "XXX", "XXX_COMMENT"
    );

    @Override
    public List<ReviewIssue> analyze(CompilationUnit compilationUnit) {

        List<ReviewIssue> issues = new ArrayList<>();

        List<Comment> comments = new ArrayList<>();

        compilationUnit.getComment().ifPresent(comments::add);
        comments.addAll(compilationUnit.getAllContainedComments());

        for (Comment comment : comments) {

            String content = comment.getContent().toUpperCase();

            for (Map.Entry<String, String> entry : COMMENT_TYPES.entrySet()) {

                if (content.contains(entry.getKey())) {

                    issues.add(createIssue(comment, entry.getValue()));

                    break;
                }

            }

        }

        return issues;
    }

    private ReviewIssue createIssue(Comment comment, String rule) {

        return ReviewIssue.builder()
                .rule(rule)
                .severity("LOW")
                .message(comment.getContent().trim())
                .line(comment.getBegin()
                        .map(position -> position.line)
                        .orElse(0))
                .build();

    }

}