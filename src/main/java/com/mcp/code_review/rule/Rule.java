package com.mcp.code_review.rule;



import com.github.javaparser.ast.CompilationUnit;
import com.mcp.code_review.model.ReviewIssue;

import java.util.List;

public interface Rule {

    List<ReviewIssue> analyze(CompilationUnit compilationUnit);

}